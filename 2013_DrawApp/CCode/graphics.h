enum colour {black,blue,cyan,darkgray,gray,green,lightgray,magenta,orange,pink,red,white,yellow};
typedef enum colour colour;

void drawLine(int,int,int,int);
void drawRect(int,int,int,int);
void drawOval(int,int,int,int);
void drawArc(int,int,int,int,int,int);
void fillRect(int,int,int,int);
void drawString(char*,int,int);
void turtleMoveForward(int distance);
void tutleTurnRight(int angle);
void turtleTurnLeft(int angle);
void drawImage(char* s, int x, int y, int width, int height);

void setColour(colour);