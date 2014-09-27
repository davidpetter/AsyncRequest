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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Cached thread pool factory
 * Convenient wrapper around getting the executor service in order to reuse the chached
 * thread pool
 */
public class CachedThreadPoolFactory {

    private static volatile ExecutorService sCachedThreadPool;

    private CachedThreadPoolFactory() {
        throw new RuntimeException("Illegal access");
    }

    /**
     * Get the thread pool instance using lazy instantiation
     *
     * @return ThreadPool singleton
     */
    public static ExecutorService getCachedThreadPool() {
        synchronized (CachedThreadPoolFactory.class) {
            if (sCachedThreadPool == null) {
                sCachedThreadPool = Executors.newCachedThreadPool();
            } else if (sCachedThreadPool.isShutdown() || sCachedThreadPool.isTerminated()) {
                sCachedThreadPool = Executors.newCachedThreadPool();
            }
        }
        return sCachedThreadPool;
    }


    /**
     * Shut down the thread pool
     */
    public static void shutDown() {
        synchronized (CachedThreadPoolFactory.class) {
            if (sCachedThreadPool == null) {
                //NOP
            } else if (!sCachedThreadPool.isShutdown() && !sCachedThreadPool.isTerminated()) {
                sCachedThreadPool.shutdown();
                sCachedThreadPool = null;
            }
        }

    }
}
