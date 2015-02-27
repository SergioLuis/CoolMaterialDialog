/**
 * Copyright 2015 Sergio Luis Para
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.sergioluis.dialog;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Utils class to avoid deprecation warnings in the main code.
 *
 * @author Sergio Luis Para
 * @version 1.0.1
 */
public class ViewUtils {

    /**
     * Set {@code v View}'s background to {@code d Drawable}.
     *
     * @param v view whose background is to be set.
     * @param d new v's background.
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("deprecated")
    public static void setBackgroundDrawable(View v, Drawable d) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackgroundDrawable(d);
        } else {
            v.setBackground(d);
        }
    }
}
