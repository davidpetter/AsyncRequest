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
package se.dp.test;

import java.util.HashMap;
import java.util.Map;

import se.dp.concurrency.AsyncResponseHandler;

/**
 * Process a response and return dummy data
 */
public abstract class DummyResponseHandler extends AsyncResponseHandler<String, Map<String, Integer>> {

    /**
     * Process the response
     *
     * @param response
     * @return
     */
    @Override
    public Map<String, Integer> processResponse(String response) {
        try {
            //Simulate doing some processing
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            //NOP
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("Test", new Integer(1000));
        return map;
    }

}
