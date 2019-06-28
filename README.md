# AsyncRequest library
A lightweight AsyncTask replacement.

Perform background requests and response processing and publish result, i.e processed response, on the UI thread.

The implementation uses a cached thread pool executor that is ideally used for short operations.
If you need to keep the threads running for longer period of time change the thread pool
executor.

## Classes overview

### AsyncRequest
Super class that handles the common things related to request execution, i.e request lifecycle.
Extend to implement the request
### AsyncResponseHandler
Super class that when extended enables response processing before returning the result of the response.
### ChachedThreadPoolFactory
Factory for reusing a single new cached instance thread pool.
