/**
 * Copyright 2014 David Pettersson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package se.dp.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Abstract request class for handling request lifecycle callbacks
 */
public abstract class AsyncRequest<TResponse, TResult> implements IAsyncRequest<TResponse, TResult> {

    //Request states
    protected static final int STATE_IDLE = 0;
    protected static final int STATE_READY_TO_EXECUTE = 1;
    protected static final int STATE_EXECUTING = 2;
    protected static final int STATE_CANCELED = 3;

    //Execution message types
    protected static final int SUCCESS_MESSAGE = 1;
    protected static final int FAILURE_MESSAGE = 2;
    protected static final int CANCEL_MESSAGE = 3;
    protected static final int DONE_MESSAGE = 4;

    //Instance request variables
    protected IAsyncResponseHandler<TResponse, TResult> mResponseHandler;
    private TResponse mResponse;
    private int mRequestId;
    private volatile int mState = STATE_IDLE;

    //Private callback handler
    protected final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case SUCCESS_MESSAGE:
                    mResponseHandler.onSuccess(AsyncRequest.this, (TResult) message.obj);
                    break;
                case FAILURE_MESSAGE:
                    mResponseHandler.onFailure(AsyncRequest.this, (Exception) message.obj);
                    break;
                case CANCEL_MESSAGE:
                    mResponseHandler.onCancel(AsyncRequest.this);
                    break;
                case DONE_MESSAGE:
                    mState = STATE_IDLE;
                    break;
            }
        }
    };

    /**
     * Perform the request execution and send the request response to the
     * response handler
     */
    @Override
    public void run() {
        TResponse response = null;
        TResult result = null;
        try {
            if (mState == STATE_READY_TO_EXECUTE) {
                mState = STATE_EXECUTING;
                response = performRequest();
                mResponse = response;
                if (mState == STATE_EXECUTING) {
                    result = mResponseHandler.processResponse(response);
                    if (mState == STATE_EXECUTING) {
                        postMessageToMainThread(SUCCESS_MESSAGE, result);
                        return; //Success, a good place to return
                    }
                }
            }
            //Reaching this point since execution is aborted for some reason
            postMessageToMainThread(CANCEL_MESSAGE, null);
        } catch (Exception e) {
            //Reaching this point since execution is aborted for some reason
            postMessageToMainThread(FAILURE_MESSAGE, e);
        } finally {
            //Execution is done, make sure to reset the request
            postMessageToMainThread(DONE_MESSAGE, null);
        }
    }

    /**
     * Set the response handler
     *
     * @param responseHandler, the class that is responsible for handling the result
     * @return
     */
    public IAsyncRequest setResponseHandler(IAsyncResponseHandler<TResponse, TResult> responseHandler) {
        mResponseHandler = responseHandler;
        return this;
    }

    /**
     * Execute the request
     *
     * @return true if the request will begin executing
     */
    @Override
    public boolean execute() {
        synchronized (this) {
            if (mState == STATE_IDLE) {
                mState = STATE_READY_TO_EXECUTE;
                CachedThreadPoolFactory.getCachedThreadPool().execute(this);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Determine if the request is executing
     *
     * @return
     */
    @Override
    public boolean isExecuting() {
        return (mState == STATE_EXECUTING);
    }

    @Override
    public void cancel() {
        mState = STATE_CANCELED;
    }

    @Override
    public IAsyncRequest setRequestId(int requestId) {
        mRequestId = requestId;
        return this;
    }

    @Override
    public int getRequestId() {
        return mRequestId;
    }

    @Override
    public TResponse getResponse() {
        return mResponse;
    }

    /**
     * Send message to listener to notify about execution
     */
    protected void postMessageToMainThread(int whatMessage, Object object) {
        Handler handler = mHandler;
        Message message = handler.obtainMessage(whatMessage, object);
        handler.sendMessage(message);
    }

    /**
     * Implement in subclass to perform the request
     *
     * @return the response of the request
     */
    protected abstract TResponse performRequest() throws Exception;
}
