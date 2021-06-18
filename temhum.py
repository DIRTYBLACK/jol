import RPi.GPIO as GPIO
import Adafruit_DHT as dht #DHT22 라이브러리

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM) #회로의 GPIO번호를 사용

DHT = 4 # 데이터 핀 설정

h, t = dht.read_retry(dht.DHT22, DHT)

'''
def read_data(): #온도, 습도 읽어서 터미널에 표시해줌

    h, t = dht.read_retry(dht.DHT22, DHT)
    print('온도 = {:.1f}C 습도 = {:.1f}%'.format(t, h))
    
    return h, t
'''

def check_data():

    global h
    global t
    h, t = dht.read_retry(dht.DHT22, DHT)
    return h, t

