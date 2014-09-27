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

/**
 * Abstract class extend and use for convenience when implementing the interface
 */
public abstract class AsyncResponseHandler<TResponse, TResult> implements IAsyncResponseHandler<TResponse, TResult> {

    /**
     * Callback that processes the response, override this method to
     * process the response and return a result
     *
     * @param response, the response that should be processed
     * @return null, override this value to return a proper result
     */
    @Override
    public TResult processResponse(TResponse response) {
        return null;
    }

    /**
     *
     * @param request, the request that failed
     * @param exception
     */
    @Override
    abstract public void onFailure(IAsyncRequest<TResponse, TResult> request, Exception exception);

    /**
     * @param request, the request that succeeded
     * @param result
     */
    @Override
    abstract public void onSuccess(IAsyncRequest<TResponse, TResult> request, TResult result);

}