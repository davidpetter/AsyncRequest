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

/*
 * IRequest interface
 * Interface to abstract the Request implementation
 *
 */
public interface IAsyncRequest<TResponse, TResult> extends Runnable {

    /**
     * Get the response of the request
     *
     * @return
     */
    public TResponse getResponse();

    /**
     * Set listener
     */
    /**
     * Call to notify listener that execution succeeded
     *
     * @param responseHandler, the class that is responsible for handling the result
     */
    public IAsyncRequest setResponseHandler(IAsyncResponseHandler<TResponse, TResult> responseHandler);

    /**
     * Set request id, set an id that can be used to identify the request
     *
     * @param requestId, request id
     * @return the request instance
     */
    public IAsyncRequest setRequestId(int requestId);

    /**
     * Get the identifier of the request
     *
     * @return get the set identifier, default value is 0
     */
    public int getRequestId();

    /**
     * Executes the request on the the request thread pool
     * The request will obtain a request executor from the pool and use
     * it to execute it self and then return it back to the pool when used.
     *
     * @return true if the request is successfully added to the execution queue,
     * will return false if request is already running
     */
    public boolean execute();

    /**
     * Get it the request is currently executing
     *
     * @return true if the request is executing, default value is false
     */
    public boolean isExecuting();

    /**
     * Cancel the request
     *
     * @return, return the request instance
     */
    public void cancel();

}