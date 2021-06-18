import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM) #회로의 GPIO번호를 사용

water_D0 = 18
water_A0 = 27

GPIO.setup(water_D0, GPIO.IN)

def waterfunc():

    if GPIO.input(water_D0) == False:
        w = 1
        print("water")
    if GPIO.input(water_D0) == True:
        w = 0
        print("no water")
        return w

def check_water():
 
    if GPIO.input(water_D0) == False:
        w = 1
    if GPIO.input(water_D0) == True:
        w = 0
        return w