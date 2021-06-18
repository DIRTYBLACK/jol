import RPi.GPIO as GPIO
import time
import temhum
import water
import relay
import post_data

def runall():
    try:
        while True:
            water.waterfunc()
            relay.on_relay()
            htdata = temhum.check_data()
            print(htdata)
            post_data.http_post_data(htdata)
            time.sleep(1)

    except KeyboardInterrupt:
        print("Terminated by Keyboard") 
        GPIO.cleanup()

runall()