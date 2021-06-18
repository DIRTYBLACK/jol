import post_data
import relay
import water
import temhum
import socket
import time
import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

HOST = ""
PORT = 8091
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print ('Socket created')
s.bind((HOST, PORT))
print ('Socket bind complete')
s.listen(1)
print ('Socket now listening')

#파이 컨트롤 함수
def do_some_stuffs_with_input(phone_send_data):
    #휴대폰에서 오는 값에따라 on, off
    if phone_send_data == "on":
        relay.on_relay()
    if phone_send_data == "off":
        relay.off_relay()

    return phone_send_data

try:
    while True:
        
        #접속 승인
        conn, addr = s.accept()
        print("Connected by ", addr)

        #데이터 수신
        data = conn.recv(1024)
        data = data.decode("utf8").strip()
        if not data: break
        print("Received: " + data)

        #수신한 데이터로 파이를 컨트롤 
        res = do_some_stuffs_with_input(data)
        print("파이 동작 :" + res)
        
        #db저장
        htdata = temhum.check_data()
        print(htdata)
        post_data.http_post_data(htdata)
        
        temhum.check_data()
        #conn.sendall(str(format(temhum.check_data())).encode("utf-8"))
        conn.sendall(str('온도 = {:.1f}°C\n'.format(temhum.t)).encode("utf-8"))
        conn.sendall(str('습도 = {:.1f}%\n'.format(temhum.h)).encode("utf-8"))

        if GPIO.input(water.water_D0) == False:
            command = ("water")
            conn.sendall(command.encode("utf-8")) 
        if GPIO.input(water.water_D0) == True:
            command = ("no water")
            conn.sendall(command.encode("utf-8"))
        else:#클라이언트에게 답을 보냄
            conn.sendall(res.encode("utf-8"))
            time.sleep(2)
        conn.close()
        #연결 닫기
    s.close()
    
except KeyboardInterrupt:
        print("Terminated by Keyboard") 
        GPIO.cleanup()