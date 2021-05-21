## SpritePacker

This application will combine multiple png images into one png image.  This application assumes that all sprites are the same size, please specify the maximum sprite height and widths if you have different sizes.

Usage:
-c,--columns <arg>   The number of columns in the sprite sheet output.
 -h,--help            Prints this message
 -i,--input <arg>     Folder location of individual sprites.
 -o,--output <arg>    Filename and location for sprite sheet output.
                      Default is spriteSheet.png saved to the folder
                      location containing the individual sprites.
 -p,--padding <arg>   Padding between sprites in sprite sheet output.
                      Default is 0px.
 -r,--rows <arg>      The number of rows in the sprite sheet output.
 -x,--width <arg>     The maximum width of the individual sprites in
                      pixels. Default is first selected sprites width.
 -y,--height <arg>    The maximum height of the individual sprites in
                      pixels. Default is first selected sprites height.
