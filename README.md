# posture_recognizer
Posture Recognizer is a mobile application that recognizes human
body postures such as sitting,standing, walking, climbing stairs implemented using the
Convolutional Neural Network model which takes input values from Accelerometer and
Gyroscope to identify various postures of a user.The Accelerometer and Gyroscope outputs
three values each to represent x,y and z coordinates. These six values are used in both training
and testing the CNN. The data are too small to train so 128 values of both the sensors are
stacked as frames with a dimension of 128x6. Likewise n number of frames are formed by
stacking up. In CNN I have used 1D convolution operation and trained it. Then the trained
model is extracted in the form of .tflite and used in a mobile application.
