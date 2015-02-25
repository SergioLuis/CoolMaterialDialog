package es.sergioluis.materialmenudialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

/**
 * Highly customizable dialog window featuring (mostly) Material Design guidelines.
 *
 * @author Sergio Luis Para
 * @version 1.0
 */
@SuppressWarnings("unused")
public class MaterialMenuDialog extends Dialog implements View.OnTouchListener, View.OnClickListener {

    private Context mContext;

    private View.OnClickListener primaryListener;
    private View.OnClickListener secondaryListener;

    private ViewHolder h;

    private Drawable primaryCircle;
    private Drawable primaryCircleDark;
    private Drawable primaryCircleLight;

    private Drawable secondaryCircle;
    private Drawable secondaryCircleDark;
    private Drawable secondaryCircleLight;

    private boolean dismissOnPrimary = false;
    private boolean dismissOnSecondary = false;

    /**
     * Creates a Dialog window as described <a href="http://sergioluis.es/materialdialog">here</a>
     *
     * @param context application's context. Please use {@code YourActivity.this} instead of {@code getApplicationContext()}
     */
    @SuppressLint("deprecated")
    @SuppressWarnings("deprecation")
    public MaterialMenuDialog(Context context) {
        super(context);

        mContext = context;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog);

        h = new ViewHolder(this);

        primaryCircle = context.getResources().getDrawable(R.drawable.primary_circle);
        primaryCircleDark = context.getResources().getDrawable(R.drawable.primary_circle_dark);
        primaryCircleLight = context.getResources().getDrawable(R.drawable.primary_circle_light);

        secondaryCircle = context.getResources().getDrawable(R.drawable.secondary_circle);
        secondaryCircleDark = context.getResources().getDrawable(R.drawable.secondary_circle_dark);
        secondaryCircleLight = context.getResources().getDrawable(R.drawable.secondary_circle_light);

        h.primaryButton.setBackgroundDrawable(primaryCircle);
        h.secondaryButton.setBackgroundDrawable(secondaryCircle);
    }

    @Override
    public void setTitle(CharSequence title) {
        h.headerTitle.setText(title);
    }

    @Override
    public void setTitle(int resId) {
        h.headerTitle.setText(resId);
    }

    /**
     * Set dialog's title color to {@code color}.</br>
     * Default title text color is {@code #FFFFFF}.
     *
     * @param color new text's color.
     */
    public void setTitleTextColor(int color) {
        h.headerTitle.setTextColor(color);
    }

    /**
     * Sets dialog's title text size to {@code size}.</br>
     * Default title text size is {@value 25sp}.
     *
     * @param size new text size, in sp.
     */
    public void setTitleTextSize(float size) {
        h.headerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * Set dialog's title text shadow.
     *
     * @param radius radius of the shadow. By default, {@value 6}
     * @param dx x distance from its drwan position. By default, {@value 2}
     * @param dy y distance from its drwan position. By default, {@value 2}
     * @param color shadow color, by default black {@code #000}
     */
    public void setTitleTextShadow(float radius, float dx, float dy, int color){
        h.headerTitle.setShadowLayer(radius, dx, dy, color);
    }

    /**
     * Set dialog's header background color to {@code color}.</br>
     * Default header's background color is {@code #37474F}.</br>
     *
     * @param color new header's background color, in RGB integer representation.
     */
    public void setHeaderBackgroundColor(int color) {
        h.header.setBackgroundColor(color);
    }

    /**
     * Set dialog's header background to the specified {@code drawable}.</br>
     * Default's header background is {@code #37474F}.</br>
     * This overrides previous calls to {@link #setHeaderBackgroundColor(int)} and
     * {@link #setHeaderBackgroundResource(int)}
     *
     * @param drawable new header's background {@code drawable}.
     */
    @SuppressLint("deprecated")
    @SuppressWarnings("deprecation")
    public void setHeaderBackgroundDrawable(Drawable drawable) {
        h.headerBackgroundImage.setImageDrawable(drawable);
        h.headerBackgroundImage.setVisibility(View.VISIBLE);
    }

    /**
     * Set dialog's header background to the specified resource.</br>
     * Default header's background is {@code #37474F}.</br>
     * This overrides previous calls to {@link #setHeaderBackgroundColor(int)} and
     * {@link #setHeaderBackgroundDrawable(android.graphics.drawable.Drawable)}
     *
     * @param resId new header's background resource file identifier.
     */
    public void setHeaderBackgroundResource(int resId) {
        h.headerBackgroundImage.setImageResource(resId);
        h.headerBackgroundImage.setVisibility(View.VISIBLE);
    }

    /**
     * Set dialog's header background image scale type. Useful only with {@link #setHeaderBackgroundResource(int)}
     * and {@link #setHeaderBackgroundDrawable(android.graphics.drawable.Drawable)}.</br>
     * By default, scale type is {@code centerCrop}
     *
     * @param scale new header's background scale type.
     */
    public void setHeaderBackgroundScaleType(ImageView.ScaleType scale) {
        h.headerBackgroundImage.setScaleType(scale);
    }

    /**
     * Set dialog's header image to the specified {@code drawable}.</br>
     * Header image has no resource nor drawable by default.</br>
     * This overrides previous calls to {@link #setPrimaryHeaderImageResource(int)}.
     *
     * @param drawable new header's image.
     */
    public void setPrimaryHeaderImageDrawable(Drawable drawable) {
        h.headerImage.setImageDrawable(drawable);
    }

    /**
     * Set dialog's header image to the specified resource.</br>
     * Header image has no resource nor drawable by default.</br>
     * This overrides previous calls to {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     *
     * @param resId new header's image resource file identifier.
     */
    public void setPrimaryHeaderImageResource(int resId) {
        h.headerImage.setImageResource(resId);
    }

    /**
     * Set dialog's header image's scale type. Remember that, by default, there is no image, so this
     * method is useful only with {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     * and {@link #setPrimaryHeaderImageResource(int)}.
     *
     * @param scale new header image's scale type.
     */
    public void setPrimaryHeaderImageScaleType(ImageView.ScaleType scale) {
        h.headerImage.setScaleType(scale);
    }

    /**
     * Hide dialog's header image.</br>
     * Header image has no resource nor drawable by default, but its {@code ImageView} is {@code visible}.</br>
     * This overrides previous calls to (and leave with no efect future calls to)
     * {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)} and
     * {@link #setPrimaryHeaderImageResource(int)}.
     */
    public void hidePrimaryHeaderImage() {
        h.headerImage.setVisibility(View.INVISIBLE);
    }

    /**
     * Set dialog's header image's background to the specified {@code drawable}.</br>
     * You can use this if you want a bigger image in the header, instead of using {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     * or {@link #setPrimaryHeaderImageResource(int)}.</br>
     * By default, mentioned background is a white circle. New background will be shaped as a circle.</br>
     *
     * This overrides previous calls to {@link #setSecondaryHeaderImageResource(int)}
     *
     * @param drawable new header's image's background.
     */
    @SuppressLint("deprecated")
    @SuppressWarnings("deprecation")
    public void setSecondaryHeaderImageDrawable(Drawable drawable) {
        h.headerCircle.setImageDrawable(drawable);
    }

    /**
     * Set dialog's header image's background to the specified resource.</br>
     * You can use this if you want a bigger image in the header, instead of using {@link #setPrimaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     * or {@link #setPrimaryHeaderImageResource(int)}.</br>
     * By default, mentioned background is a white circle. New background will be shaped as a circle.</br>
     *
     * This overrides previous calls to {@link #setSecondaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     *
     * @param resId new header image's background resource identifier.
     */
    public void setSecondaryHeaderImageResource(int resId) {
        h.headerCircle.setImageResource(resId);
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
        h.headerCircle.setScaleType(scale);
    }

    /**
     * Hide dialog's header image's background.</br>
     * By default, mentioned background is a white circle.</br>
     *
     * This leaves with no effect previous (and future) calls to {@link #setSecondaryHeaderImageDrawable(android.graphics.drawable.Drawable)}
     * and {@link #setSecondaryHeaderImageResource(int)}
     */
    public void hideSecondaryHeaderImage() {
        h.headerCircle.setVisibility(View.INVISIBLE);
    }

    /**
     * Register a callback to be invoked when the primary button is clicked.</br>
     * Primary button is the bigger one, at the right of the dialog, and it wont be visible until
     * it has a {@code View.OnClickListener} callback registered.
     *
     * @param l callback to be invoked when the primary button is clicked.
     * @param dismiss if the dialog must be dismissed once the callback action is complete.
     */
    public void setOnPrimaryButtonClickListener(View.OnClickListener l, boolean dismiss) {
        h.primaryButton.setVisibility(View.VISIBLE);
        h.primaryImage.setVisibility(View.VISIBLE);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            h.primaryButtonShadow.setVisibility(View.VISIBLE);
        }

        h.primaryButton.setOnClickListener(this);
        h.primaryImage.setOnClickListener(this);
        h.primaryButton.setOnTouchListener(this);
        h.primaryImage.setOnTouchListener(this);

        primaryListener = l;
        dismissOnPrimary = dismiss;
    }

    /**
     * Set primary button's image to the specified {@code drawable}, centered in.</br>
     * Primary button is the bigger one, at the right of the dialog, and it wont be visible until it
     * has a {@code View.OnClickListener} callback registered.</br>
     *
     * By default, primary button's image is the ic_action_accept stock image.
     *
     * @see #setOnPrimaryButtonClickListener(android.view.View.OnClickListener, boolean)
     *
     * @param drawable new primary button's image.
     */
    public void setPrimaryButtonImageDrawable(Drawable drawable) {
        h.primaryImage.setImageDrawable(drawable);
    }

    /**
     * Set primary button's image to the specified resource, centered in.</br>
     * Primary button is the bigger one, at the right of the dialog, and it wont be visible until it
     * has a {@code View.OnClickListener} callback registered.</br>
     *
     * By default, primary button's image is the ic_action_accept stock image.
     *
     * @see #setOnPrimaryButtonClickListener(android.view.View.OnClickListener, boolean)
     *
     * @param resId new primary button's resource file identifier.
     */
    public void setPrimaryButtonImageResource(int resId) {
        h.primaryImage.setImageResource(resId);
    }

    /**
     * Define the color of the primary button when it is in the default state.</br>
     * Default primary button's default color is {@code #E91E63}, Pink 500 in the Material Design
     * Color guide.
     *
     * @param color new color for the button when it is in its default state, in RGBA int representation.
     */
    public void setPrimaryButtonDefaultColor(int color) {
        primaryCircle.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        h.primaryButton.invalidate();
    }

    /**
     * Define the color of the primary button when it is pressed (activated).</br>
     * Default primary button's pressed color is {@code #AD1457}, Pink 900 in the Material Design
     * Color guide.
     *
     * @param color new color for the button when it is pressed, in RGBA int representarion.
     */
    public void setPrimaryButtonPressedColor(int color) {
        primaryCircleDark.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Define the color of the primary button when it is focused (when the button is highlighted
     * using the trackball or directional pad).</br>
     * Default primary button's focused color is {@code #EC407A}, Pink 400 in the Material Design
     * Color guide.
     *
     * @param color new color for the button when it is focused, in RGBA int representation.
     */
    public void setPrimaryButtonFocusedColor(int color) {
        primaryCircleLight.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Register a callback to be invoked when the secondary button is clicked.</br>
     * Secondary button is the smaller one, and it wont be visible until it has a
     * {@code View.OnClickListener} callback registered.
     *
     * @param l callback to be invoked when the secondary button is clicked.
     * @param dismiss if the dialog must be dismissed once the callback action is complete.
     */
    public void setOnSecondaryButtonClickListener(View.OnClickListener l, boolean dismiss) {
        h.secondaryButton.setVisibility(View.VISIBLE);
        h.secondaryImage.setVisibility(View.VISIBLE);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            h.secondaryButtonShadow.setVisibility(View.VISIBLE);
        }

        h.secondaryButton.setOnClickListener(this);
        h.secondaryImage.setOnClickListener(this);
        h.secondaryButton.setOnTouchListener(this);
        h.secondaryImage.setOnTouchListener(this);

        secondaryListener = l;
        dismissOnSecondary = dismiss;
    }

    /**
     * Set primary button's image to the specified {@code drawable}, centered in.</br>
     * Secondary button is the smaller one, and it wont be visible until it as a
     * {@code View.OnClickListener} callback registered.</br>
     *
     * By default, primary button's image is the ic_action_cancel stock image.
     *
     * @see #setOnSecondaryButtonClickListener(android.view.View.OnClickListener, boolean)
     *
     * @param drawable new primary button's image.
     */
    public void setSecondaryButtonImageDrawable(Drawable drawable) {
        h.secondaryImage.setImageDrawable(drawable);
    }

    /**
     * Set secondary button's image to the specified resource, centered in.</br>
     * Secondary button is the smaller one, and it wont be visible until it as a
     * {@code View.OnClickListener} callback registered.</br>
     *
     * By default, primary secondary's image is the ic_action_cancel stock image.
     *
     * @see #setOnPrimaryButtonClickListener(android.view.View.OnClickListener, boolean)
     *
     * @param resId new secondary button's resource file identifier.
     */
    public void setSecondaryButtonImageResource(int resId) {
        h.secondaryImage.setImageResource(resId);
    }

    /**
     * Define the color of the secondary button when it is in the default state.</br>
     * Default secondary button's default color is {@code #FFC107}, Amber 500 in the Material Design
     * Color guide.
     *
     * @param color new color for the button when it is in its default state, in RGBA int representation.
     */
    public void setSecondaryButtonDefaultColor(int color) {
        secondaryCircle.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        h.secondaryButton.invalidate();
    }

    /**
     * Define the color of the secondary button when it is pressed (activated).</br>
     * Default secondary button's pressed color is {@code #FF6F00}, Amber 900 in the Material Design
     * Color guide.
     *
     * @param color new color for the button when it is pressed, in RGBA int representarion.
     */
    public void setSecondaryButtonPressedColor(int color) {
        secondaryCircleDark.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Define the color of the secondary button when it is focused (when the button is highlighted
     * using the trackball or directional pad).</br>
     * Default secondary button's focused color is {@code #FFCA28}, Amber 400 in the Material Design
     * Color guide.
     *
     * @param color new color for the button when it is focused, in RGBA int representation.
     */
    public void setSecondaryButtonFocusedColor(int color) {
        secondaryCircle.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     *
     * @param v
     */
    public void setInnerView(View v) {
        LinearLayout parentLayout = (LinearLayout) h.innerView.getParent();
        int index = parentLayout.indexOfChild(h.innerView);
        parentLayout.removeViewAt(index);
        parentLayout.addView(v, index);
    }

    @Override
    @SuppressWarnings("deprecation")
    @SuppressLint("deprecated")
    public boolean onTouch(View v, MotionEvent event) {
        int viewId = v.getId();
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (viewId == R.id.primary_button_circle || viewId == R.id.primary_button_image) {
                    h.primaryButton.setBackgroundDrawable(primaryCircleDark);
                } else if (viewId == R.id.secondary_button_circle || viewId == R.id.secondary_button_image) {
                    h.secondaryButton.setBackgroundDrawable(secondaryCircleDark);
                }
                return false;
            case MotionEvent.ACTION_UP:
                if (viewId == R.id.primary_button_circle || viewId == R.id.primary_button_image) {
                    h.primaryButton.setBackgroundDrawable(primaryCircle);
                } else if (viewId == R.id.secondary_button_circle || viewId == R.id.secondary_button_image) {
                    h.secondaryButton.setBackgroundDrawable(secondaryCircle);
                }
                return false;
            case MotionEvent.ACTION_HOVER_ENTER:
                if (viewId == R.id.primary_button_circle || viewId == R.id.primary_button_image) {
                    h.primaryButton.setBackgroundDrawable(primaryCircleLight);
                } else if (viewId == R.id.secondary_button_circle || viewId == R.id.secondary_button_image) {
                    h.secondaryButton.setBackgroundDrawable(secondaryCircleLight);
                }
                return false;
            case MotionEvent.ACTION_HOVER_EXIT:
                if (viewId == R.id.primary_button_circle || viewId == R.id.primary_button_image) {
                    h.primaryButton.setBackgroundDrawable(primaryCircle);
                } else if (viewId == R.id.secondary_button_circle || viewId == R.id.secondary_button_image) {
                    h.secondaryButton.setBackgroundDrawable(secondaryCircle);
                }
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();

        if(vId == h.primaryButton.getId() || vId == h.primaryImage.getId()){
            primaryListener.onClick(h.primaryButton);
            if (dismissOnPrimary) this.dismiss();
        } else {
            secondaryListener.onClick(h.secondaryButton);
            if (dismissOnSecondary) this.dismiss();
        }
    }

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

        public FrameLayout innerView;

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

            innerView = (FrameLayout) d.findViewById(R.id.inner_view);

        }

    }

    public static class Builder {

        private Context context;
        private MaterialMenuDialog dialog;

        /**
         * Constructor using a context for this builder and the {@link es.sergioluis.materialmenudialog.MaterialMenuDialog}
         * it creates.
         *
         * @param context application's context.
         */
        public Builder(Context context) {
            this.context = context;
            dialog = new MaterialMenuDialog(context);
        }

        /**
         *
         * @param title
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitle(CharSequence title) {
            dialog.setTitle(title);
            return this;
        }

        /**
         *
         * @param resId
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitle(int resId){
            dialog.setTitle(resId);
            return this;
        }

        /**
         *
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitleTextColor(int color) {
            dialog.setTitleTextColor(color);
            return this;
        }

        /**
         *
         * @param size
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitleTextSize(float size) {
            dialog.setTitleTextSize(size);
            return this;
        }

        /**
         *
         * @param radius
         * @param dx
         * @param dy
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setTitleTextShadow(float radius, float dx, float dy, int color) {
            dialog.setTitleTextShadow(radius, dx, dy, color);
            return this;
        }

        /**
         *
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundColor(int color) {
            dialog.setHeaderBackgroundColor(color);
            return this;
        }

        /**
         *
         * @param drawable
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundDrawable(Drawable drawable) {
            dialog.setHeaderBackgroundDrawable(drawable);
            return this;
        }

        /**
         *
         * @param resId
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundResource(int resId) {
            dialog.setHeaderBackgroundResource(resId);
            return this;
        }

        /**
         *
         * @param scale
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setHeaderBackgroundScaleType(ImageView.ScaleType scale) {
            dialog.setHeaderBackgroundScaleType(scale);
            return this;
        }

        /**
         *
         * @param drawable
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryHeaderImageDrawable(Drawable drawable) {
            dialog.setPrimaryHeaderImageDrawable(drawable);
            return this;
        }

        /**
         *
         * @param resId
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryHeaderImageResource(int resId) {
            dialog.setPrimaryHeaderImageResource(resId);
            return this;
        }

        /**
         *
         * @param scale
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryHeaderImageScaleType(ImageView.ScaleType scale) {
            dialog.setPrimaryHeaderImageScaleType(scale);
            return this;
        }

        /**
         *
         * @return this Builder object to allow chaining of methods.
         */
        public Builder hideprimaryHeaderImage() {
            dialog.hidePrimaryHeaderImage();
            return this;
        }

        /**
         *
         * @param drawable
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryHeaderImageDrawable(Drawable drawable) {
            dialog.setSecondaryHeaderImageDrawable(drawable);
            return this;
        }

        /**
         *
         * @param resId
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryHeaderImageResource(int resId) {
            dialog.setSecondaryHeaderImageResource(resId);
            return this;
        }

        /**
         *
         * @param scale
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryHeaderImageScaleType(ImageView.ScaleType scale) {
            dialog.setSecondaryHeaderImageScaleType(scale);
            return this;
        }

        /**
         *
         * @return this Builder object to allow chaining of methods.
         */
        public Builder hideSecondaryHeaderImage() {
            dialog.hideSecondaryHeaderImage();
            return this;
        }

        /**
         *
         * @param l
         * @param dismiss
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setOnPrimaryButtonClickListener(View.OnClickListener l, boolean dismiss) {
            dialog.setOnPrimaryButtonClickListener(l, dismiss);
            return this;
        }

        /**
         *
         * @param drawable
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonImageDrawable(Drawable drawable) {
            dialog.setPrimaryButtonImageDrawable(drawable);
            return this;
        }

        /**
         *
         * @param resId
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonImageResource(int resId) {
            dialog.setPrimaryButtonImageResource(resId);
            return this;
        }

        /**
         *
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonDefaultColor(int color) {
            dialog.setPrimaryButtonDefaultColor(color);
            return this;
        }

        /**
         *
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonFocusedColor(int color) {
            dialog.setPrimaryButtonFocusedColor(color);
            return this;
        }

        /**
         *
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setPrimaryButtonPressedColor(int color) {
            dialog.setPrimaryButtonPressedColor(color);
            return this;
        }

        /**
         *
         * @param l
         * @param dismiss
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setOnSecondaryButtonClickListener(View.OnClickListener l, boolean dismiss) {
            dialog.setOnSecondaryButtonClickListener(l, dismiss);
            return this;
        }

        /**
         *
         * @param drawable
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonImageDrawable(Drawable drawable) {
            dialog.setSecondaryButtonImageDrawable(drawable);
            return this;
        }

        /**
         *
         * @param resId
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonImageResource(int resId) {
            dialog.setSecondaryButtonImageResource(resId);
            return this;
        }

        /**
         *
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonDefaultColor(int color) {
            dialog.setSecondaryButtonDefaultColor(color);
            return this;
        }

        /**
         *
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonFocusedColor(int color) {
            dialog.setSecondaryButtonFocusedColor(color);
            return this;
        }

        /**
         *
         * @param color
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setSecondaryButtonPressedColor(int color) {
            dialog.setSecondaryButtonPressedColor(color);
            return this;
        }

        /**
         * Inflates de View in the layout file provided and sets it into the Dialog.
         *
         * @param resId
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setInnerView(int resId) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(resId, null);
            return setInnerView(v);
        }

        /**
         *
         * @param v
         * @return this Builder object to allow chaining of methods.
         */
        public Builder setInnerView(View v) {
            dialog.setInnerView(v);
            return this;
        }

        /**
         * Creates a {@link es.sergioluis.materialmenudialog.MaterialMenuDialog} with the arguments
         * suplied to the builder.
         *
         * @return the {@link es.sergioluis.materialmenudialog.MaterialMenuDialog} created.
         */
        public MaterialMenuDialog create() {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            return dialog;
        }

    }
}
