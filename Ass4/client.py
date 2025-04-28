import socket
import time
import threading

HOST = '127.0.0.1'
PORT = 65432
CLIENT_PORT = None  # unique for each client

def client_node(initial_offset):
    local_time = time.time() + initial_offset
    print(f"[Client @ Offset {initial_offset}] Current time: {local_time:.2f}")
    
    # Step 1: Send current time to coordinator
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, 0))  # bind to a random port
        global CLIENT_PORT
        CLIENT_PORT = s.getsockname()[1]
        s.connect((HOST, PORT))
        s.send(str(local_time).encode())
    
    # Step 2: Wait for adjustment on own port
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, CLIENT_PORT))
        s.listen(1)
        conn, _ = s.accept()
        adjustment = float(conn.recv(1024).decode())
        adjusted_time = local_time + adjustment
        print(f"[Client @ Offset {initial_offset}] Received adjustment: {adjustment:.2f}")
        print(f"[Client @ Offset {initial_offset}] Adjusted time: {adjusted_time:.2f}")

if __name__ == '__main__':
    # Simulate multiple clients by running this script with different offsets
    offset = float(input("Enter initial clock offset in seconds (e.g. 3.2): "))
    client_node(offset)
