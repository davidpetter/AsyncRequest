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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import se.dp.async.R;
import se.dp.concurrency.AsyncRequest;
import se.dp.concurrency.IAsyncRequest;


public class TestActivity extends Activity {

    private TextView mDummyTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_test);
        mDummyTextResult = (TextView) findViewById(R.id.dummyResult);
    }

    public void sendRequest(View view) {
        mDummyTextResult.setText(R.string.requesting);
        mDummy.execute();
    }

    @Override
    protected void onDestroy() {
        mDummy.cancel();
        super.onDestroy();
    }


    private final IAsyncRequest<String, Map<String, Integer>> mDummy = new DummyRequest().setResponseHandler(new DummyResponseHandler() {

        @Override
        public void onSuccess(IAsyncRequest<String, Map<String, Integer>> request, Map<String, Integer> map) {
            String concatenatedResult = new StringBuilder(request.getResponse()).append(":").append(map.toString()).toString();
            mDummyTextResult.setText(concatenatedResult);
        }

        @Override
        public void onFailure(IAsyncRequest<String, Map<String, Integer>> request, Exception exception) {
            mDummyTextResult.setText(exception.getLocalizedMessage());
        }

        @Override
        public void onCancel(AsyncRequest<String, Map<String, Integer>> request) {
            mDummyTextResult.setText(R.string.canceled);
        }
    });

}
