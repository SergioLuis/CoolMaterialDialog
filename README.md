# CoolMaterialDialog

![Vanilla dialog](https://img.imgur.com/PIdcT8h.jpg)

* __Header primary image__ is the Andy image. By default, there is not drawable nor resource set. You can set the image using _setPrimaryHeaderImageDrawable(Drawable drawable)_ or _setPrimaryHeaderImageResource(int resId)_. By default, it has no scale type. You can change that using _setPrimaryHeaderImageScaleType(ImageView.ScaleType type)_.
* __Header secondary image__ is the white circle behing Andy. By default, is a XML circle drawable. It will still be circle shaped even if you change it. You can change the header secondary image using _setSecondaryHeaderImageDrawable(Drawable drawable)_ or _setSecondaryHeaderImageResource(int resId)_. By default, its scale type is centerCrop0 You can change that using _setSecondaryHeaderImageScaleType(ImageView.ScaleType type)_. You can hide the secondary image using _hideSecondaryHeaderImage()_.
* __Title__ is the title of the dialog, "Vanilla" in our example. You can change the title using _setTitle(CharSequence title)_ or _setTitle(int resId)_.
    * __Title text color__ is, by default, white. You can change it using _setTitleTextColor(int color).
    * __Title text size__ is, by default, 25sp. You can chang it using _setTitleTextSize(float size)_, with size in sp.
    * __Title text shadow__ is intended to make the text easier to read. By default, is black with a radius of 6 and x and y distance of 2. You can change it using _setTitleTextShadow(float radius, float dx, float dy, int color).
* __Header background__ is, by default, Material Blue Grey 900. You can change the color or set an image.
    * __ Header background color__ is changed using _setHeaderBackgroundColor(int color)_.
    * __Header background image__ is set using _setHeaderBackgroundDrawable(Drawable drawable)_ or _setHeaderBackgroundResource(int resId)_. This overrides the background color and can not be undone. You can chage the background image scale type using _setHeaderBackgroundScaleType_.
* __Primary button__ is the bigger one, Pink 500, on the right.
    * __Primary button drawable__ is the _ic\_action\_accept_ image, on top of the primary button. You can use _setPrimaryButtonImageResource(int resId)_ or _setPrimaryButtonImageDrawable(Drawable drawable)_ to change it.
    * __Primary button default color__ is, by default, Pink 500. You can use _setPrimaryButtonDefaultColor(int color)_ to change it.
    * __Primary button pressed color__ is, by default, Pink 900. You can use _setPrimaryButtonPressedColor(int color)_ to change it.
    * __Primary button focused color__ is, by default, Pink 400. You can use _setPrimaryButtonFocusedColor(int color)_ to change it.
    * __Primary button click listener__ is set with _setOnPrimaryButtonClickListener(View.OnClickListener l, boolean dismiss)_. The dismiss flag indicates if the dialog should be dismissed once the action in the OnClick callback is completed. The button won't be visible until it has a OnClickListener callback attached.
* __Secondary button__ is the smaller one, Amber 500, on the left.
    * __Secondary button drawable__ is the _ic\_action\_cancel_ image, on top of the secondary button. You can use _setSecondaryButtonImageResource(int resId)_ or _setSecondaryButtonImageDrawable(Drawable drawable)_ to change it.
    * __Secondary button default color__ is, by default, Amber 500. You can use _setSecondaryButtonDefaultColor(int color)_ to change it.
    * __Secondary button pressed color__ is, by default, Amber 900. You can use _setSecondaryButtonPressedColor(int color)_ to change it.
    * __Secondary button focused color__ is, by default, Pink 400. You can use _setSecondaryButtonFocusedColor(int color)_ to change it.
    * __Secondary button click listener__ is set with _setOnSecondaryButtonClickListener(View.OnClickListener l, boolean dismiss)_. The dismiss flag indicates if the dialog should be dismissed once the action in the OnClick callback is completed. The button won't be visible until it has a OnClickListener callback attached.  


        MaterialMenuDialog.Builder defaultBuilder = new MaterialMenuDialog.Builder(MainActivity.this);
                defaultBuilder.setPrimaryHeaderImageResource(R.drawable.ic_launcher)
                        .setOnPrimaryButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Primary button", Toast.LENGTH_SHORT).show();
                            }
                        }, true)
                        .setOnSecondaryButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Secondary button", Toast.LENGTH_SHORT).show();
                            }
                        }, true)
                        .setTitle("Vanilla")
                        .create().show();  


![Delibes dialog](https://img.imgur.com/x3z4bws.jpg)  


        MaterialMenuDialog.Builder uvaBuilder = new MaterialMenuDialog.Builder(MainActivity.this);
                uvaBuilder.setSecondaryHeaderImageResource(R.drawable.uva_logo)
                        .setHeaderBackgroundResource(R.drawable.campus_miguel_delibes)
                        .setTitle("Campus Miguel Delibes")
                        .setTitleTextShadow(10, 2, 2, Color.BLACK)
                        .setOnPrimaryButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }, true)
                        .setPrimaryButtonImageResource(R.drawable.ic_action_call)
                        .setPrimaryButtonDefaultColor(Color.parseColor("#2196F3"))
                        .setPrimaryButtonPressedColor(Color.parseColor("#0D47A1"))
                        .setOnSecondaryButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }, true)
                        .setSecondaryButtonImageResource(R.drawable.ic_action_directions)
                        .setSecondaryButtonDefaultColor(Color.parseColor("#FFC107"))
                        .setSecondaryButtonPressedColor(Color.parseColor("#FF6F00"))
                        .setInnerView(R.layout.content_uva);
                uvaBuilder.create().show();

* __Inner view__ is your main content. It is set through _setInnerView(int resId)_ or _setInnerView(View v)_.  


![Waters dialog](https://img.imgur.com/TkQiHT3.jpg)  


        MaterialMenuDialog.Builder rogerBuilder = new MaterialMenuDialog.Builder(MainActivity.this);
                rogerBuilder.setSecondaryHeaderImageResource(R.drawable.roger_waters)
                        .setTitle("Roger Waters")
                        .setOnPrimaryButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "onPrimaryButtonClick", Toast.LENGTH_SHORT).show();
                            }
                        }, true)
                        .setHeaderBackgroundResource(R.drawable.dark_side)
                        .setInnerView(R.layout.content_roger_waters)
                        .create().show();