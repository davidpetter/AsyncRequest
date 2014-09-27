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
 * Request response interface, handle request responses
 */
public interface IAsyncResponseHandler<TResponse, TResult> {

    /**
     * Callback on worker thread to process the response of the request
     *
     * @param response, the response that is to be processed
     * @return the result of the processed response, default value is null
     */
    public TResult processResponse(TResponse response);

    /**
     * Callback on main thread if request fails
     *
     * @param request, the request that failed
     */
    public void onFailure(IAsyncRequest<TResponse, TResult> request, Exception e);

    /**
     * Callback on main thread if request succeeds
     *
     * @param request, the request that succeeded
     */
    public void onSuccess(IAsyncRequest<TResponse, TResult> request, TResult result);

    /**
     * Callback on main thread if request fails
     *
     * @param request, the request that got canceled
     */
    public void onCancel(AsyncRequest<TResponse, TResult> request);
}
