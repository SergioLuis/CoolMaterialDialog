# CoolMaterialDialog

#### Some Material Design in a highly customizable dialog. Min API: 14

###### Usage instructions

At the moment, this library is not available on Maven (it will be soon), but you can import the Android Studio project and use it. [Here is the 1.0.1 version](https://github.com/SergioLuis/CoolMaterialDialog/releases/tag/v1.0.1). Inside the zip you'll find the following:

* A how-to add and use the Android Studio project.
* Complete HTML Javadoc.
* LICENSE file.

-----------------------------------------------------------

## Vanilla dialog  

![Vanilla dialog](https://img.imgur.com/VNNhXm2.png)  


```java
CoolMaterialDialog.Builder defaultBuilder = new CoolMaterialDialog.Builder(MainActivity.this);
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
```

* __Header primary image__ is the Andy image. By default, there is not drawable nor resource set. You can set the image using ```setPrimaryHeaderImageDrawable(Drawable drawable)``` or ```setPrimaryHeaderImageResource(int resId)```. By default, it has no scale type. You can change that using ```setPrimaryHeaderImageScaleType(ImageView.ScaleType type)```.
* __Header secondary image__ is the white circle behing Andy. By default, is a XML circle drawable. It will still be circle shaped even if you change it. You can change the header secondary image using ```setSecondaryHeaderImageDrawable(Drawable drawable)``` or ```setSecondaryHeaderImageResource(int resId)```. By default, its scale type is centerCrop0 You can change that using ```setSecondaryHeaderImageScaleType(ImageView.ScaleType type)```. You can hide the secondary image using ```hideSecondaryHeaderImage()```.
* __Title__ is the title of the dialog, "Vanilla" in our example. You can change the title using ```setTitle(CharSequence title)``` or ```setTitle(int resId)```.
    * __Title text color__ is, by default, white. You can change it using ```setTitleTextColor(int color)```.
    * __Title text size__ is, by default, 25sp. You can chang it using ```setTitleTextSize(float size)```, with size in sp.
    * __Title text shadow__ is intended to make the text easier to read. By default, is black with a radius of 6 and x and y distance of 2. You can change it using ```setTitleTextShadow(float radius, float dx, float dy, int color)```.
* __Header background__ is, by default, Material Blue Grey 900. You can change the color or set an image.
    * __Header background color__ is changed using ```setHeaderBackgroundColor(int color)```.
    * __Header background image__ is set using ```setHeaderBackgroundDrawable(Drawable drawable)``` or ```setHeaderBackgroundResource(int resId)```. This overrides the background color and can not be undone. You can chage the background image scale type using ```setHeaderBackgroundScaleType(ImageView.ScaleType type)```.
* __Primary button__ is the bigger one, Pink 500, on the right.
    * __Primary button drawable__ is the _ic\_action\_accept_ image, on top of the primary button. You can use ```setPrimaryButtonImageResource(int resId)``` or ```setPrimaryButtonImageDrawable(Drawable drawable)``` to change it.
    * __Primary button default color__ is, by default, Pink 500. You can use ```setPrimaryButtonDefaultColor(int color)``` to change it.
    * __Primary button pressed color__ is, by default, Pink 900. You can use ```setPrimaryButtonPressedColor(int color)``` to change it.
    * __Primary button focused color__ is, by default, Pink 400. You can use ```setPrimaryButtonFocusedColor(int color)``` to change it.
    * __Primary button click listener__ is set with ```setOnPrimaryButtonClickListener(View.OnClickListener l, boolean dismiss)```. The dismiss flag indicates if the dialog should be dismissed once the action in the ```OnClick callback``` is completed. The button won't be visible until it has a ```OnClickListener``` callback attached.
* __Secondary button__ is the smaller one, Amber 500, on the left.
    * __Secondary button drawable__ is the _ic\_action\_cancel_ image, on top of the secondary button. You can use ```setSecondaryButtonImageResource(int resId)``` or ```setSecondaryButtonImageDrawable(Drawable drawable)``` to change it.
    * __Secondary button default color__ is, by default, Amber 500. You can use ```setSecondaryButtonDefaultColor(int color)``` to change it.
    * __Secondary button pressed color__ is, by default, Amber 900. You can use ```setSecondaryButtonPressedColor(int color)``` to change it.
    * __Secondary button focused color__ is, by default, Amber 400. You can use ```setSecondaryButtonFocusedColor(int color)``` to change it.
    * __Secondary button click listener__ is set with ```setOnSecondaryButtonClickListener(View.OnClickListener l, boolean dismiss)```. The dismiss flag indicates if the dialog should be dismissed once the action in the ```OnClick``` callback is completed. The button won't be visible until it has a ```OnClickListener``` callback attached.  

--------------------------------------------------------------------------

## Delibes dialog

![Delibes dialog](https://img.imgur.com/9GXALFQ.png)  

```java
CoolMaterialDialog.Builder uvaBuilder = new CoolMaterialDialog.Builder(MainActivity.this);
uvaBuilder.setSecondaryHeaderImageResource(R.drawable.uva_logo)
    .setHeaderBackgroundResource(R.drawable.campus_miguel_delibes)
    .setTitle("Campus Miguel Delibes")
    .setTitleTextShadow(10, 2, 2, Color.BLACK)
    .setOnPrimaryButtonClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // None
        }
    }, true)
    .setPrimaryButtonImageResource(R.drawable.ic_action_call)
    .setPrimaryButton(R.color.blue_default, R.color.blue_pressed, R.color.blue_focused, 
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call stuff
            }
        }, true)
    .setSecondaryButtonImageResource(R.drawable.ic_action_directions)
    .setSecondaryButton(R.color.yellow_default, R.color.yellow_pressed, R.color.yellow_focused,
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Maps stuff
            }
        }, true)
    .setInnerView(R.layout.content_uva);
    .create().show();
```

* __Inner view__ is your main content. It is set through ```setInnerView(int resId)``` or ```setInnerView(View v)```.  

-----------------------------------------------------------------

## Waters dialog

-----------------------------------------------------------------

## Waters dialog

![Waters dialog](https://img.imgur.com/9pbNszq.png)  

```java
CoolMaterialDialog.Builder rogerBuilder = new CoolMaterialDialog.Builder(MainActivity.this);
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
