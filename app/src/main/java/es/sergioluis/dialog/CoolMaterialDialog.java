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

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import es.sergioluis.materialmenudialog.R;

/**
 * Highly customizable dialog window featuring (most of the) Material Design guidelines.
 * <p/>
 * The dialog has two buttons, called {@code primaryButton} and {@code secondaryButton}. The primary
 * button is the bigger one, so it should perform the main action of the dialog, such as accept or
 * play music. The secondary button is smaller and on the left of the primary, so it should perform
 * a secondary (and maybe complementary) action, like cancel or show help.
 * <p/>
 * Content view is not scrollable by default, and this Dialog class provide no help to make it
 * scrollable, so you must implement that functionality in your own layout. Have in mind that a Dialog
 * is not supposed to take a lot of screen size, so please make sure your Dialog looks fine among
 * different screen sizes and resolutions, and, of course, landscape mode.
 *
 * @author Sergio Luis Para
 * @version 1.0.1
 */
public class CoolMaterialDialog extends Dialog {

    private Context mContext;

    private View.OnClickListener primaryListener;
    private View.OnClickListener secondaryListener;

    private View.OnClickListener clickListener;
    private View.OnTouchListener touchListener;

    private ViewHolder vh;
    private View contentView;

    private Drawable primaryCircle;
    private Drawable primaryCircleDark;
    private Drawable primaryCircleLight;

    private Drawable secondaryCircle;
    private Drawable secondaryCircleDark;
    private Drawable secondaryCircleLight;

    private boolean dismissOnPrimary = false;
    private boolean dismissOnSecondary = false;

    /**
     * Creates a Dialog window as described <a href="https://github.com/SergioLuis/CoolMaterialDialog">here</a>
     *
     * @param context application's context. Please use {@code YourActivity.this} instead of {@code getApplicationContext()}
     */
    public CoolMaterialDialog(Context context) {
        super(context);

        mContext = context;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.dialog);

        vh = new ViewHolder(this);

        primaryCircle = context.getResources().getDrawable(R.drawable.primary_circle);
        primaryCircleDark = context.getResources().getDrawable(R.drawable.primary_circle_dark);
        primaryCircleLight = context.getResources().getDrawable(R.drawable.primary_circle_light);

        secondaryCircle = context.getResources().getDrawable(R.drawable.secondary_circle);
        secondaryCircleDark = context.getResources().getDrawable(R.drawable.secondary_circle_dark);
        secondaryCircleLight = context.getResources().getDrawable(R.drawable.secondary_circle_light);

        ViewUtils.setBackgroundDrawable(vh.primaryButton, primaryCircle);
        ViewUtils.setBackgroundDrawable(vh.secondaryButton, secondaryCircle);

        prepareClickListener();
        prepareTouchListener();
    }

    private void prepareClickListener() {
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vId = v.getId();

                if (vId == vh.primaryButton.getId() || vId == vh.primaryImage.getId()) {
                    primaryListener.onClick(vh.primaryButton);
                    if (dismissOnPrimary) dismiss();
                } else {
                    secondaryListener.onClick(vh.secondaryButton);
                    if (dismissOnSecondary) dismiss();
                }
            }
        };
    }

    private void prepareTouchListener() {
        touchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int viewId = v.getId();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        if (viewId == R.id.primary_button_circle || viewId == R.id.primary_button_image) {
                            ViewUtils.setBackgroundDrawable(vh.primaryButton, primaryCircleDark);
                        } else if (viewId == R.id.secondary_button_circle || viewId == R.id.secondary_button_image) {
                            ViewUtils.setBackgroundDrawable(vh.secondaryButton, secondaryCircleDark);
                        }
                        return false;
                    case MotionEvent.ACTION_UP:
                        if (viewId == R.id.primary_button_circle || viewId == R.id.primary_button_image) {
                            ViewUtils.setBackgroundDrawable(vh.primaryButton, primaryCircle);
                        } else if (viewId == R.id.secondary_button_circle || viewId == R.id.secondary_button_image) {
                            ViewUtils.setBackgroundDrawable(vh.secondaryButton, secondaryCircle);
                        }
                        return false;
                    case MotionEvent.ACTION_HOVER_ENTER:
                        if (viewId == R.id.primary_button_circle || viewId == R.id.primary_button_image) {
                            ViewUtils.setBackgroundDrawable(vh.primaryButton, primaryCircleLight);
                        } else if (viewId == R.id.secondary_button_circle || viewId == R.id.secondary_button_image) {
                            ViewUtils.setBackgroundDrawable(vh.secondaryButton, secondaryCircleLight);
                        }
                        return false;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        if (viewId == R.id.primary_button_circle || viewId == R.id.primary_button_image) {
                            ViewUtils.setBackgroundDrawable(vh.primaryButton, primaryCircle);
                        } else if (viewId == R.id.secondary_button_circle || viewId == R.id.secondary_button_image) {
                            ViewUtils.setBackgroundDrawable(vh.secondaryButton, secondaryCircle);
                        }
                        return false;
                    default:
                        return false;
                }
            }
        };
    }

    /**
     * Set the title text for this dialog's window.
     *
     * @param title the new text to display in the title.
     */
    @Override
    public void setTitle(CharSequence title) {
        vh.headerTitle.setText(title);
    }

    /**
     * Set the title text for this dialog's window.
     *
     * @param resId the resource id of the new text to display in the title.
     */
    @Override
    public void setTitle(@StringRes int resId) {
        vh.headerTitle.setText(resId);
    }

    /**
     * Set dialog's title color to {@code color}.</br>
     * Default title text color is {@code #FFFFFF}.
     *
     * @param colorResId new text's color resource.
     */
    public void setTitleTextColor(@ColorRes int colorResId) {
        vh.headerTitle.setTextColor(mContext.getResources().getColor(colorResId));
    }

    /**
     * Set the title's text size of this dialog's window to {@code size}.</br>
     * Default title's text size is {@value 25sp}.
     *
     * @param size new text size, in sp.
     */
    public void setTitleTextSize(float size) {
        vh.headerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * Set dialog's title's text shadow.
     *
     * @param radius radius of the shadow in dp. By default, {@value 6}
     * @param dx     x distance in dp from its drawn position. By default, {@value 2}
     * @param dy     y distance in dp from its drawn position. By default, {@value 2}
     * @param argbColor  shadow color, by default black {@code #000}, in ARGB representation.
     */
    public void setTitleTextShadow(float radius, float dx, float dy, int argbColor) {
        vh.headerTitle.setShadowLayer(radius, dx, dy, argbColor);
    }

    /**
     * Set dialog's header background color to {@code colorResId}.</br>
     * Default header's background color is {@code #37474F}.</br>
     *
     * @param colorResId new header's background color resource.
     */
    public void setHeaderBackgroundColor(@ColorRes int colorResId) {
        vh.header.setBackgroundColor(mContext.getResources().getColor(colorResId));
    }

    /**
     * Set dialog's header background to the specified {@code drawable}.</br>
     * Default header's background is {@code #37474F}.</br>
     * This overrides previous calls to {@link #setHeaderBackgroundColor(int)} and
     * {@link #setHeaderBackgroundResource(int)}
     *
     * @param drawable new header's background {@code drawable}.
     */
    public void setHeaderBackgroundDrawable(Drawable drawable) {
        vh.headerBackgroundImage.setImageDrawable(drawable);
        vh.headerBackgroundImage.setVisibility(View.VISIBLE);
    }

    /**
     * Set dialog's header background to the specified resource.</br>
     * Default header's background is {@code #37474F}.</br>
     * This overrides previous calls to {@link #setHeaderBackgroundColor(int)} and
     * {@link #setHeaderBackgroundDrawable(android.graphics.drawable.Drawable)}
     *
     * @param resId new header's background resource file identifier.
     */
    public void setHeaderBackgroundResource(@DrawableRes int resId) {
        vh.headerBackgroundImage.setImageResource(resId);
        vh.headerBackgroundImage.setVisibility(View.VISIBLE);
    }

    /**
     * Set dialog's header background image scale type. Useful only with {@link #setHeaderBackgroundResource(int)}
     * and {@link #setHeaderBackgroundDrawable(android.graphics.drawable.Drawable)}.</br>
     * By default, scale type is {@code centerCrop}
     *
     * @param scale new header's background scale type.
     */
    public void setHeaderBackgroundScaleType(ImageView.ScaleType scale) {
        vh.headerBackgroundImage.setScaleType(scale);
    }

    /**
     * Set dialog's header image to the specified {@code drawable}.</br>
     * Header image has no resource nor drawable by default.</br>
     * This overrides previous calls to {@link #setPrimaryHeaderImageResource(int)}.
     *
     * @param drawable new header's image.
     */
    public void setPrimaryHeaderImageDrawable(Drawable drawable) {
        vh.headerImage.setImageDrawable(drawable);
    }

    /**
     * Set dialog's header image to the specified resource.</br>
     * Header image has no resource nor drawable by default.</br>
     * This overrides previous calls to {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     *
     * @param resId new header's image resource identifier.
     */
    public void setPrimaryHeaderImageResource(@DrawableRes int resId) {
        vh.headerImage.setImageResource(resId);
    }

    /**
     * Set dialog's header image's scale type. Remember that, by default, there is no image, so this
     * method is useful only with {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     * and {@link #setPrimaryHeaderImageResource(int)}.
     *
     * @param scale new header image's scale type.
     */
    public void setPrimaryHeaderImageScaleType(ImageView.ScaleType scale) {
        vh.headerImage.setScaleType(scale);
    }

    /**
     * Hide dialog's header image.</br>
     * Header image has no resource nor drawable by default, but its {@code ImageView} is {@code visible}.</br>
     * This overrides previous calls to (and leave with no efect future calls to)
     * {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)} and
     * {@link #setPrimaryHeaderImageResource(int)}.
     */
    public void hidePrimaryHeaderImage() {
        vh.headerImage.setVisibility(View.INVISIBLE);
    }

    /**
     * Set dialog's header image's background to the specified {@code drawable}.</br>
     * You can use this if you want a bigger image in the header, instead of using {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     * or {@link #setPrimaryHeaderImageResource(int)}.</br>
     * By default, mentioned background is a white circle. New background will be shaped as a circle.</br>
     * <p/>
     * This overrides previous calls to {@link #setSecondaryHeaderImageResource(int)}
     *
     * @param drawable new header's image's background.
     */
    public void setSecondaryHeaderImageDrawable(Drawable drawable) {
        vh.headerCircle.setImageDrawable(drawable);
    }

    /**
     * Set dialog's header image's background to the specified resource.</br>
     * You can use this if you want a bigger image in the header, instead of using {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     * or {@link #setPrimaryHeaderImageResource(int)}.</br>
     * By default, mentioned background is a white circle. New background will be shaped as a circle.</br>
     * <p/>
     * This overrides previous calls to {@link #setSecondaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     *
     * @param resId new header image's background resource identifier.
     */
    public void setSecondaryHeaderImageResource(@DrawableRes int resId) {
        vh.headerCircle.setImageResource(resId);
    }

    /**
     * Set dialog's header image's background scale type. Remember that default background is
     * white, and background's shape is a white circle (and, no matter what, will remain a circle).
     * So this method is useful only with {@link #setSecondaryHeaderImageResource(int)} and
     * {@link #setSecondaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     *
     * @param scale new header image's background's scale type
     */
    public void setSecondaryHeaderImageScaleType(ImageView.ScaleType scale) {
        vh.headerCircle.setScaleType(scale);
    }

    /**
     * Hide dialog's header image's background.</br>
     * By default, mentioned background is a white circle.</br>
     * <p/>
     * This leaves with no effect previous (and future) calls to {@link #setSecondaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     * and {@link #setSecondaryHeaderImageResource(int)}
     */
    public void hideSecondaryHeaderImage() {
        vh.headerCircle.setVisibility(View.INVISIBLE);
    }

    /**
     * Register a callback to be invoked when the primary button is clicked.</br>
     * Primary button is the bigger one, at the right of the dialog, and it wont be visible until
     * it has a {@code View.OnClickListener} callback registered.
     *
     * @param l       callback to be invoked when the primary button is clicked.
     * @param dismiss if the dialog must be dismissed once the callback action is complete.
     */
    public void setOnPrimaryButtonClickListener(View.OnClickListener l, boolean dismiss) {
        vh.primaryButton.setVisibility(View.VISIBLE);
        vh.primaryImage.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            vh.primaryButtonShadow.setVisibility(View.VISIBLE);
        }

        vh.primaryButton.setOnClickListener(clickListener);
        vh.primaryImage.setOnClickListener(clickListener);
        vh.primaryButton.setOnTouchListener(touchListener);
        vh.primaryImage.setOnTouchListener(touchListener);

        primaryListener = l;
        dismissOnPrimary = dismiss;
    }

    /**
     * Set primary button's image to the specified {@code drawable}, centered in.</br>
     * Primary button is the bigger one, at the right of the dialog, and it wont be visible until it
     * has a {@code View.OnClickListener} callback registered.</br>
     * <p/>
     * By default, primary button's image is the ic_action_accept stock image.
     *
     * @param drawable new primary button's image.
     * @see #setOnPrimaryButtonClickListener(android.view.View.OnClickListener, boolean)
     */
    public void setPrimaryButtonImageDrawable(Drawable drawable) {
        vh.primaryImage.setImageDrawable(drawable);
    }

    /**
     * Set primary button's image to the specified resource, centered in.</br>
     * Primary button is the bigger one, at the right of the dialog, and it wont be visible until it
     * has a {@code View.OnClickListener} callback registered.</br>
     * <p/>
     * By default, primary button's image is the ic_action_accept stock image.
     *
     * @param resId new primary button's resource identifier.
     * @see #setOnPrimaryButtonClickListener(android.view.View.OnClickListener, boolean)
     */
    public void setPrimaryButtonImageResource(@DrawableRes int resId) {
        vh.primaryImage.setImageResource(resId);
    }

    /**
     * Define the color of the primary button when it is in the default state.</br>
     * Default primary button's default color is {@code #E91E63}, Pink 500 in the Material Design
     * Color guide.
     *
     * @param colorResId resource id of the new color for the button when it is in its default state.
     */
    public void setPrimaryButtonDefaultColor(@ColorRes int colorResId) {
        primaryCircle.setColorFilter(mContext.getResources().getColor(colorResId),
                PorterDuff.Mode.SRC_ATOP);
        vh.primaryButton.invalidate();
    }

    /**
     * Define the color of the primary button when it is pressed (activated).</br>
     * Default primary button's pressed color is {@code #AD1457}, Pink 900 in the Material Design
     * Color guide.
     *
     * @param colorResId resource id of the new color for the button when it is pressed.
     */
    public void setPrimaryButtonPressedColor(@ColorRes int colorResId) {
        primaryCircleDark.setColorFilter(mContext.getResources().getColor(colorResId),
                PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Define the color of the primary button when it is focused (when the button is highlighted
     * using the trackball or directional pad).</br>
     * Default primary button's focused color is {@code #EC407A}, Pink 400 in the Material Design
     * Color guide.
     *
     * @param colorResId resource id of the new color for the button when it is focused.
     */
    public void setPrimaryButtonFocusedColor(@ColorRes int colorResId) {
        primaryCircleLight.setColorFilter(mContext.getResources().getColor(colorResId),
                PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Register a callback to be invoked when the secondary button is clicked.</br>
     * Secondary button is the smaller one, and it wont be visible until it has a
     * {@code View.OnClickListener} callback registered.
     *
     * @param l       callback to be invoked when the secondary button is clicked.
     * @param dismiss if the dialog must be dismissed once the callback action is complete.
     */
    public void setOnSecondaryButtonClickListener(View.OnClickListener l, boolean dismiss) {
        vh.secondaryButton.setVisibility(View.VISIBLE);
        vh.secondaryImage.setVisibility(View.VISIBLE);

        // In Lollipop the elevation attribute provides shadows.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            vh.secondaryButtonShadow.setVisibility(View.VISIBLE);
        }

        vh.secondaryButton.setOnClickListener(clickListener);
        vh.secondaryImage.setOnClickListener(clickListener);
        vh.secondaryButton.setOnTouchListener(touchListener);
        vh.secondaryImage.setOnTouchListener(touchListener);

        secondaryListener = l;
        dismissOnSecondary = dismiss;
    }

    /**
     * Set primary button's image to the specified {@code drawable}, centered in.</br>
     * Secondary button is the smaller one, and it wont be visible until it as a
     * {@code View.OnClickListener} callback registered.</br>
     * <p/>
     * By default, primary button's image is the ic_action_cancel stock image.
     *
     * @param drawable new primary button's image.
     * @see #setOnSecondaryButtonClickListener(android.view.View.OnClickListener, boolean)
     */
    public void setSecondaryButtonImageDrawable(Drawable drawable) {
        vh.secondaryImage.setImageDrawable(drawable);
    }

    /**
     * Set secondary button's image to the specified resource, centered in.</br>
     * Secondary button is the smaller one, and it wont be visible until it as a
     * {@code View.OnClickListener} callback registered.</br>
     * <p/>
     * By default, primary secondary's image is the ic_action_cancel stock image.
     *
     * @param resId new secondary button's resource identifier.
     * @see #setOnPrimaryButtonClickListener(android.view.View.OnClickListener, boolean)
     */
    public void setSecondaryButtonImageResource(@DrawableRes int resId) {
        vh.secondaryImage.setImageResource(resId);
    }

    /**
     * Define the color of the secondary button when it is in the default state.</br>
     * Default secondary button's default color is {@code #FFC107}, Amber 500 in the Material Design
     * Color guide.
     *
     * @param colorResId resource id of the new color for the button when it is in its default state.
     */
    public void setSecondaryButtonDefaultColor(@ColorRes int colorResId) {
        secondaryCircle.setColorFilter(mContext.getResources().getColor(colorResId),
                PorterDuff.Mode.SRC_ATOP);
        vh.secondaryButton.invalidate();
    }

    /**
     * Define the color of the secondary button when it is pressed (activated).</br>
     * Default secondary button's pressed color is {@code #FF6F00}, Amber 900 in the Material Design
     * Color guide.
     *
     * @param colorResId resource id of the new color for the button when it is pressed.
     */
    public void setSecondaryButtonPressedColor(@ColorRes int colorResId) {
        secondaryCircleDark.setColorFilter(mContext.getResources().getColor(colorResId),
                PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Define the color of the secondary button when it is focused (when the button is highlighted
     * using the trackball or directional pad).</br>
     * Default secondary button's focused color is {@code #FFCA28}, Amber 400 in the Material Design
     * Color guide.
     *
     * @param colorResId resource id of the new color for the button when it is focused.
     */
    public void setSecondaryButtonFocusedColor(@ColorRes int colorResId) {
        secondaryCircle.setColorFilter(mContext.getResources().getColor(colorResId),
                PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Set the inner view to be the content of the Dialog.</br>
     * The inner view, in the end, is where you encapsulate all the functionality you want to offer
     * in your dialog.</br>
     * Please keep in mind that, by default, it won't be scrollable until you define your view that
     * way, and it's a little bit tricky. For more information, visit
     * <a href="http://github.com/SergioLuis/CoolMaterialDialog">the repo</a>.
     *
     * @param v dialog's window inner view.
     */
    @Override
    public void setContentView(View v) {
        LinearLayout parentLayout = (LinearLayout) vh.contentView.getParent();
        int index = parentLayout.indexOfChild(vh.contentView);
        parentLayout.removeViewAt(index);
        parentLayout.addView(v, index);
        contentView = v;
    }

    /**
     * Look for a child view with the given id. If the view has the given id, return this view.
     * Please keep in mind that you must have set previously a content view in which hierarchy
     * you want to find the view.
     *
     * @param id the id of the desired child view.
     * @return the view that has the given id in the hierarchy or null.
     */
    @Nullable
    public View findViewById(int id) {
        if (contentView != null) {
            return contentView.findViewById(id);
        }
        return null;
    }

    /**
     * A classic ViewHolder.
     */
    private class ViewHolder {

        public RelativeLayout header;
        public CircleImageView headerCircle;
        public ImageView headerImage;
        public TextView headerTitle;

        public ImageView headerBackgroundImage;

        public View primaryButton;
        public ImageView primaryImage;
        public View primaryButtonShadow;

        public View secondaryButton;
        public ImageView secondaryImage;
        public View secondaryButtonShadow;

        public FrameLayout contentView;

        public ViewHolder(Dialog d) {

            header = (RelativeLayout) d.findViewById(R.id.header);
            headerCircle = (CircleImageView) d.findViewById(R.id.header_circle);
            headerImage = (ImageView) d.findViewById(R.id.header_image);
            headerTitle = (TextView) d.findViewById(R.id.header_text);

            headerBackgroundImage = (ImageView) d.findViewById(R.id.header_background_imageview);

            primaryButton = d.findViewById(R.id.primary_button_circle);
            primaryImage = (ImageView) d.findViewById(R.id.primary_button_image);
            primaryButtonShadow = d.findViewById(R.id.primary_button_shadow);

            secondaryButton = d.findViewById(R.id.secondary_button_circle);
            secondaryImage = (ImageView) d.findViewById(R.id.secondary_button_image);
            secondaryButtonShadow = d.findViewById(R.id.secondary_button_shadow);

            contentView = (FrameLayout) d.findViewById(R.id.inner_view);
        }

    }

    /**
     * A builder class to make an instance of {@link es.sergioluis.dialog.CoolMaterialDialog}.
     * Althought you can make your dialog just using the public methods of your instance of {@link es.sergioluis.dialog.CoolMaterialDialog},
     * dialog's width may be incorrect. The {@link #create()} method prevents that issue. Plus, this
     * Builder class provide an easy way to do method chaining and some super-charged methods to
     * do more in less code.
     *
     * @author Sergio Luis Para
     * @version 1.0.1
     */
    public static class Builder {

        private Context context;
        private CoolMaterialDialog dialog;

        /**
         * Constructor using a context for this builder and the
         * {@link CoolMaterialDialog} it creates.
         *
         * @param context application's context.
         */
        public Builder(Context context) {
            this.context = context;
            dialog = new CoolMaterialDialog(context);
        }

        /**
         * Set the title text of the Dialog.
         *
         * @param title the new title to be displayed in the dialog.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitle(CharSequence title) {
            dialog.setTitle(title);
            return this;
        }

        /**
         * Set the title text of the Dialog.
         *
         * @param resId the resource id of the new title to be displayed in the dialog.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitle(@StringRes int resId) {
            dialog.setTitle(resId);
            return this;
        }

        /**
         * Set the title's text color of the Dialog.
         *
         * @param colorResId new title's text color resource.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitleTextColor(@ColorRes int colorResId) {
            dialog.setTitleTextColor(colorResId);
            return this;
        }

        /**
         * Set the title's text size of the Dialog.</br>
         * By default, that size is {@value 25sp}.
         *
         * @param size new title's text size in sp.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitleTextSize(float size) {
            dialog.setTitleTextSize(size);
            return this;
        }

        /**
         * Set the title text and its color.
         *
         * @param resId   new dialog's title text resource.
         * @param colorResId new dialog's title text color resource.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitle(@StringRes int resId, @ColorRes int colorResId) {
            dialog.setTitle(resId);
            dialog.setTitleTextColor(colorResId);
            return this;
        }

        /**
         * Set the title text and its color.
         *
         * @param title   new dialog's title text.
         * @param colorResourceId new dialog's title text color resource.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitle(CharSequence title, @ColorRes int colorResourceId) {
            dialog.setTitle(title);
            dialog.setTitleTextColor(colorResourceId);
            return this;
        }

        /**
         * Set the title's text shadow of the Dialog.
         *
         * @param radius radius of the shadow. By default, {@value 6}
         * @param dx     x distance from its drwan position. By default, {@value 2}
         * @param dy     y distance from its drwan position. By default, {@value 2}
         * @param argbColor  shadow's color, by default black {@code #000}, in ARGB integer representation.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitleTextShadow(float radius, float dx, float dy, @ColorRes int argbColor) {
            dialog.setTitleTextShadow(radius, dx, dy, argbColor);
            return this;
        }

        /**
         * Set dialog's header background color to {@code color}. Default header's background color
         * is #37474F.
         *
         * @param colorResId new header's background color resource.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundColor(@ColorRes int colorResId) {
            dialog.setHeaderBackgroundColor(colorResId);
            return this;
        }

        /**
         * Set dialog's header background image to {@code drawable}. This method overrides previous calls
         * to {@link #setHeaderBackgroundColor(int)} and {@link #setHeaderBackgroundResource(int)}.
         *
         * @param drawable new header's background image.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundDrawable(Drawable drawable) {
            dialog.setHeaderBackgroundDrawable(drawable);
            return this;
        }

        /**
         * Set dialog's header background to the resource specified. This method overrides previous
         * calls to {@link #setHeaderBackgroundDrawable(android.graphics.drawable.Drawable)} and
         * {@link #setHeaderBackgroundColor(int)}.
         *
         * @param resId resource identifier.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundResource(@DrawableRes int resId) {
            dialog.setHeaderBackgroundResource(resId);
            return this;
        }

        /**
         * Set dialog's header background image scale type. Please have in mind that, by default,
         * the dialog has no header background image (only a solid color), so this method is useful
         * only with {@link #setHeaderBackgroundResource(int)} or
         * {@link #setHeaderBackgroundDrawable(android.graphics.drawable.Drawable)}</br>
         * <p/>
         * By default, said scale type is {@code centerCrop}
         *
         * @param scale header's background image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundScaleType(ImageView.ScaleType scale) {
            dialog.setHeaderBackgroundScaleType(scale);
            return this;
        }

        /**
         * Set dialog's header background image.
         *
         * @param resId     new header background image.
         * @param scaleTYpe background image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundImage(@DrawableRes int resId, ImageView.ScaleType scaleTYpe) {
            dialog.setHeaderBackgroundResource(resId);
            dialog.setHeaderBackgroundScaleType(scaleTYpe);
            return this;
        }

        /**
         * Set dialog's header background image.
         *
         * @param drawable  new header background image.
         * @param scaleType background image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundImage(Drawable drawable, ImageView.ScaleType scaleType) {
            dialog.setHeaderBackgroundDrawable(drawable);
            dialog.setHeaderBackgroundScaleType(scaleType);
            return this;
        }

        /**
         * Set header's primary image drawable. By default, it has no drawable nor resource set.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param drawable new header's primary image drawable.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryHeaderImageDrawable(Drawable drawable) {
            dialog.setPrimaryHeaderImageDrawable(drawable);
            return this;
        }

        /**
         * Set header's primary image resource. By default, it has no drawable nor resource set.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param resId new header's primary image resource identifier.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryHeaderImageResource(@DrawableRes int resId) {
            dialog.setPrimaryHeaderImageResource(resId);
            return this;
        }

        /**
         * Set header's primary image scale type. By default, it is {@code centerCrop}.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param scaleType new header's primary image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryHeaderImageScaleType(ImageView.ScaleType scaleType) {
            dialog.setPrimaryHeaderImageScaleType(scaleType);
            return this;
        }

        /**
         * Set header's primary image resource. By default, it has no drawable nor resource set, but its
         * visibility attribute is {@code View.VISIBLE}.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param resId     new header's primary image resource identifier.
         * @param scaleType new header's primary image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryHeaderImage(@DrawableRes int resId, ImageView.ScaleType scaleType) {
            dialog.setPrimaryHeaderImageResource(resId);
            dialog.setPrimaryHeaderImageScaleType(scaleType);
            return this;
        }

        /**
         * Set header's primary image drawable. By default, it has no drawable nor resource set, but its
         * visibility attribute is {@code View.VISIBLE}.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param drawable  new header's primary image drawable.
         * @param scaleType new header's primary image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryHeaderImage(Drawable drawable, ImageView.ScaleType scaleType) {
            dialog.setPrimaryButtonImageDrawable(drawable);
            dialog.setPrimaryHeaderImageScaleType(scaleType);
            return this;
        }

        /**
         * Set header's primary image visibility to {@code View.INVISIBLE}. By default, said image has no drawable nor resource, and its
         * visibility is {@code View.VISIBLE}.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @return this Builder object to allow chaining of methods.
         */
        public Builder hideprimaryHeaderImage() {
            dialog.hidePrimaryHeaderImage();
            return this;
        }

        /**
         * Set header's secondary image drawable. By default, it is a XML oval shape drawable.
         * The new image will be circle shaped.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param drawable new header's secondary image drawable.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryHeaderImageDrawable(Drawable drawable) {
            dialog.setSecondaryHeaderImageDrawable(drawable);
            return this;
        }

        /**
         * Set header's secondary image resource id. By default, it is a XML oval shape drawable.
         * The new image will be circle shaped.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param resId new header's secondary image resource id.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryHeaderImageResource(@DrawableRes int resId) {
            dialog.setSecondaryHeaderImageResource(resId);
            return this;
        }

        /**
         * Set header's secondary image scale type. By default, it is {@code centerCrop}.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param scale new header's secondary image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryHeaderImageScaleType(ImageView.ScaleType scale) {
            dialog.setSecondaryHeaderImageScaleType(scale);
            return this;
        }

        /**
         * Set header's secondary image resource. By default, it is a XML oval shape drawable.
         * The new image will be circle shaped.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param resId     new header's secondary image resource id.
         * @param scaleType new header's secondary image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryHeaderImage(@DrawableRes int resId, ImageView.ScaleType scaleType) {
            dialog.setSecondaryHeaderImageResource(resId);
            dialog.setSecondaryHeaderImageScaleType(scaleType);
            return this;
        }

        /**
         * Set header's secondary image drawable. By default, it is a XML oval shape drawable.
         * The new image will be circle shaped.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param drawable  new header's secondary image drawable.
         * @param scaleType new header's secondary image scale type.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryHeaderImage(Drawable drawable, ImageView.ScaleType scaleType) {
            dialog.setSecondaryButtonImageDrawable(drawable);
            dialog.setSecondaryHeaderImageScaleType(scaleType);
            return this;
        }

        /**
         * Hide header's secondary image. By default, it is a XML oval shape drawable.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @return this Builder object to allow chaining of methods.
         */
        public Builder hideSecondaryHeaderImage() {
            dialog.hideSecondaryHeaderImage();
            return this;
        }

        /**
         * Register a callback to be invoked when the primary button is clicked. Primary button is
         * the bigger one, at the right of the dialog, and it wont be visible until it has a
         * View.OnClickListener callback registered.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param l       callback to be invoked when the primary button is clicked.
         * @param dismiss if the dialog must be dismissed once the callback action is complete.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setOnPrimaryButtonClickListener(View.OnClickListener l, boolean dismiss) {
            dialog.setOnPrimaryButtonClickListener(l, dismiss);
            return this;
        }

        /**
         * Set primary button's image to the specified drawable, centered in. Primary button is the
         * bigger one, at the right of the dialog, and it wont be visible until it has a
         * {@code View.OnClickListener} callback registered. By default, primary button's image is
         * the ic_action_accept stock image.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param drawable new primary button's image drawable.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonImageDrawable(Drawable drawable) {
            dialog.setPrimaryButtonImageDrawable(drawable);
            return this;
        }

        /**
         * Set primary button's image to the specified resource, centered in. Primary button is the
         * bigger one, at the right of the dialog, and it wont be visible until it has a
         * {@code View.OnClickListener} callback registered. By default, primary button's image is
         * the ic_action_accept stock image.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param resId new primary button's image resource identifier.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonImageResource(@DrawableRes int resId) {
            dialog.setPrimaryButtonImageResource(resId);
            return this;
        }

        /**
         * Define the color of the primary button when it is in the default state. Default primary
         * button's default color is #E91E63, Pink 500 in the Material Design Color guide.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param colorResId resource id of the new color for the button when it is in its default state.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonDefaultColor(@ColorRes int colorResId) {
            dialog.setPrimaryButtonDefaultColor(colorResId);
            return this;
        }

        /**
         * Define the color of the primary button when it is focused (when the button is highlighted
         * using the trackball or directional pad). Default primary button's focused color is
         * #EC407A, Pink 400 in the Material Design Color guide.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param colorResId resource id of the new color for the button when it is focused.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonFocusedColor(@ColorRes int colorResId) {
            dialog.setPrimaryButtonFocusedColor(colorResId);
            return this;
        }

        /**
         * Define the color of the primary button when it is pressed (activated). Default primary
         * button's pressed color is #AD1457, Pink 900 in the Material Design Color guide.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param colorResId resource id of the new color for the button when it is pressed.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonPressedColor(@ColorRes int colorResId) {
            dialog.setPrimaryButtonPressedColor(colorResId);
            return this;
        }

        /**
         * Define colors and action of the primary button.
         *
         * @param defaultColorResId color resource id of the primary button when it is in its default state.
         * @param pressedColorResId color resource id of the primary button when it is pressed (activated).
         * @param focusedColorResId color resource id of the primary button when it is focused.
         * @param l            callback to be invoked when the primary button is clicked.
         * @param dismiss      if the dialog must be dismissed once the callback action is complete.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButton(@ColorRes int defaultColorResId, @ColorRes int pressedColorResId, @ColorRes int focusedColorResId, View.OnClickListener l, boolean dismiss) {
            dialog.setPrimaryButtonDefaultColor(defaultColorResId);
            dialog.setPrimaryButtonPressedColor(pressedColorResId);
            dialog.setPrimaryButtonFocusedColor(focusedColorResId);
            dialog.setOnPrimaryButtonClickListener(l, dismiss);
            return this;
        }

        /**
         * Register a callback to be invoked when the secondary button is clicked. Secondary button
         * is the smaller one, and it wont be visible until it has a {@code View.OnClickListener} callback
         * registered.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param l       callback to be invoked when the primary button is clicked.
         * @param dismiss if the dialog must be dismissed once the callback action is complete.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setOnSecondaryButtonClickListener(View.OnClickListener l, boolean dismiss) {
            dialog.setOnSecondaryButtonClickListener(l, dismiss);
            return this;
        }

        /**
         * Set secondary button's image to the specified drawable, centered in. Secondary button is the
         * smaller one, at the left of the dialog, and it wont be visible until it has a
         * {@code View.OnClickListener} callback registered. By default, secondary button's image is
         * the ic_action_cancel stock image.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param drawable new secondary button's image drawable.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonImageDrawable(Drawable drawable) {
            dialog.setSecondaryButtonImageDrawable(drawable);
            return this;
        }

        /**
         * Set secondary button's image to the specified resource, centered in. Secondary button is the
         * smaller one, at the left of the dialog, and it wont be visible until it has a
         * {@code View.OnClickListener} callback registered. By default, secondary button's image is
         * the ic_action_cancel stock image.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param resId new secondary button's image resource identifier.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonImageResource(@DrawableRes int resId) {
            dialog.setSecondaryButtonImageResource(resId);
            return this;
        }

        /**
         * Define the color of the secondary button when it is in the default state. Default
         * secondary button's default color is #FFC107, Amber 500 in the Material Design Color guide.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param colorResId resource id of the new color for the button when it is in its default state.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonDefaultColor(@ColorRes int colorResId) {
            dialog.setSecondaryButtonDefaultColor(colorResId);
            return this;
        }

        /**
         * Define the color of the secondary button when it is focused (when the button is
         * highlighted using the trackball or directional pad). Default secondary button's
         * focused color is #FFCA28, Amber 400 in the Material Design Color guide.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param colorResId resource id of the new color for the button when it is focused.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonFocusedColor(@ColorRes int colorResId) {
            dialog.setSecondaryButtonFocusedColor(colorResId);
            return this;
        }

        /**
         * Define the color of the secondary button when it is pressed (activated). Default secondary
         * button's pressed color is #FF6F00, Amber 900 in the Material Design Color guide.</br>
         * For more info about dialog's layout, please read
         * <a href="https://github.com/SergioLuis/CoolMaterialDialog">the doc</a>.
         *
         * @param colorResId resource id of the new color for the button when it is pressed.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonPressedColor(@ColorRes int colorResId) {
            dialog.setSecondaryButtonPressedColor(colorResId);
            return this;
        }

        /**
         * Define colors and action of the secondary button.
         *
         * @param defaultColorResId color resource id of the secondary button when it is in its default state.
         * @param pressedColorResId color resource id of the secondary button when it is pressed (activated).
         * @param focusedColorResId color resource id of the secondary button when it is focused.
         * @param l            callback to be invoked when the secondary button is clicked.
         * @param dismiss      if the dialog must be dismissed once the callback action is complete.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButton(@ColorRes int defaultColorResId, @ColorRes int pressedColorResId, @ColorRes int focusedColorResId, View.OnClickListener l, boolean dismiss) {
            dialog.setSecondaryButtonDefaultColor(defaultColorResId);
            dialog.setSecondaryButtonPressedColor(pressedColorResId);
            dialog.setSecondaryButtonFocusedColor(focusedColorResId);
            dialog.setOnSecondaryButtonClickListener(l, dismiss);
            return this;
        }

        /**
         * Inflate de View in the layout provided and set it into the Dialog.
         *
         * @param resId resource identifier of the desired inner view.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setContentView(@LayoutRes int resId) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(resId, null);
            return setContentView(v);
        }

        /**
         * Set the provided view inside the dialog.
         *
         * @param v desired inner view.
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setContentView(View v) {
            dialog.setContentView(v);
            return this;
        }

        /**
         * Creates a {@link CoolMaterialDialog} with the arguments
         * given to the builder.
         *
         * @return the {@link CoolMaterialDialog} created.
         */
        public CoolMaterialDialog create() {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            return dialog;
        }
    }
}
