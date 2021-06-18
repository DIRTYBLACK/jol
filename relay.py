import RPi.GPIO as GPIO

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM) #회로의 GPIO번호를 사용

relay = 17

GPIO.setup(relay, GPIO.OUT)

def on_relay():
    GPIO.output(relay, True)
    return

def off_relay():
    GPIO.output(relay, False)
    return