# CoolMaterialDialog

#### Some Material Design in a highly customizable dialog. Min API: 14

###### Usage instructions

If you use gradle, is just a couple of lines, I promise. First, in your project-scope build.gradle file, you must add the following:

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

And then, add the depency:

```
compile 'com.github.SergioLuis:CoolMaterialDialog:1.0.1'
```

If you use Maven, it's easy too.

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

And then, the dependency:

```xml
<dependency>
    <groupId>com.github.SergioLuis</groupId>
    <artifactId>CoolMaterialDialog</artifactId>
    <version>1.0.1</version>
</dependency>
```

If you want to read the source or have a handy HTML Javadoc, feel free to clone this repo and add it to Android Studio. The APACHE License allows you to fool around and play with the code.

-----------------------------------------------------------

## Vanilla dialog  

![Vanilla dialog](https://img.imgur.com/VNNhXm2.png)  
_Wow, that looks cool, but it must be really really difficult to use, right?_

```javas
CoolMaterialDialog.Builder defaultBuilder = new CoolMaterialDialog.Builder(MainActivity.this);
defaultBuilder.setPrimaryHeaderImageResource(R.drawable.ic_launcher)
    .setOnPrimaryButtonClickListener(new View.OnClickListener() {
    
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Primary button", Toast.LENGTH_SHORT).show();
        }
        
    }, true) // false if you don't want to dismiss the Dialog after the onClick is completed.
   .setOnSecondaryButtonClickListener(new View.OnClickListener() {
   
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Secondary button", Toast.LENGTH_SHORT).show();
        }
        
   }, true) // false if you don't want to dismiss the Dialog after the onClick is completed.
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

## UVa dialog

![Delibes dialog](https://img.imgur.com/9GXALFQ.png)  

_Sapienta aedificavit sibi domum (Knowledge built its own home)_

```java
CoolMaterialDialog.Builder uvaBuilder = new CoolMaterialDialog.Builder(MainActivity.this);
uvaBuilder.setSecondaryHeaderImageResource(R.drawable.uva_logo)
    .setHeaderBackgroundResource(R.drawable.campus_miguel_delibes)
    .setTitle(R.string.uva_dialog_title)
    .setTitleTextShadow(10, 2, 2, Color.BLACK)
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
    .setContentView(R.layout.content_uva);
    .create().show();
```

* __Inner view__ is your main content. It is set through ```setContentView(int resId)``` or ```setContentView(View v)```.  If you pass a layout identifier, the Builder will inflate it as a View and pass it to ```setContentView(View v)```, so if you want to hold a reference to the inner view and its elements (maybe you want to change a text programatically), inflate your own View, hold a reference to it and use ```setContentView(View v)```. But a ```CoolMaterialDialog::findViewById(int id)``` method is on its way.


```java
View myContent = LayoutInflater.from(getApplicationContext()).inflate(R.layout.content_uva, null);

myContent.findViewById(R.id.my_list).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Click on the list, stuff happens
    });

CoolMaterialDialog.Builder uvaBuilder = new CoolMaterialDialog.Builder(MainActivity.this);
uvaBuilder.setSecondaryHeaderImageResource(R.drawable.uva_logo)
    .setHeaderBackgroundResource(R.drawable.campus_miguel_delibes)
    .setTitle("Campus Miguel Delibes")
    .setTitleTextShadow(10, 2, 2, Color.BLACK)
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
    .setContentView(myContent); // Using our own view
    .create().show();
    
((TextView) myContent.findViewById(R.id.title)).setText("I can change elements from code!");
```

-----------------------------------------------------------------

## Waters dialog

![Waters dialog](https://img.imgur.com/9pbNszq.png) 

_Does anybody here remember Vera Lynn...?_

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
    .setContentView(R.layout.content_roger_waters)
    .create().show();
```

## LICENSE

Copyright 2015 Sergio Luis Para

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
