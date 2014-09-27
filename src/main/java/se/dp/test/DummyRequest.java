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

import java.io.IOException;
import java.net.URISyntaxException;

import se.dp.concurrency.AsyncRequest;

/**
 * Request that does a dummy request
 */
public class DummyRequest extends AsyncRequest {

    //Constructor
    public DummyRequest() {
    }

    @Override
    public String performRequest() throws URISyntaxException, IOException {
        try {
            //Simulate doing some processing
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            //NOP
        }
        return "DUMMY";
    }

}
