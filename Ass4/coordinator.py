import socket
import threading
import time

HOST = '127.0.0.1'
PORT = 65432
NUM_CLIENTS = 3
client_times = {}
lock = threading.Lock()

def handle_client(conn, addr):
    client_time = float(conn.recv(1024).decode())
    print(f"Received time {client_time} from {addr}")
    with lock:
        client_times[addr] = client_time
    conn.close()

def send_adjustments():
    all_times = list(client_times.values())
    coordinator_time = time.time()
    all_times.append(coordinator_time)
    average_time = sum(all_times) / len(all_times)
    print(f"Average time: {average_time:.2f}")
    for addr, client_time in client_times.items():
        adjustment = average_time - client_time
        client_ip, client_port = addr
        try:
            client_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            client_sock.connect((client_ip, client_port))
            client_sock.send(str(adjustment).encode())
            client_sock.close()
            print(f"Sent adjustment {adjustment:.2f} to {addr}")
        except Exception as e:
            print(f"Error sending adjustment to {addr}: {e}")

def main():
    print("Coordinator started. Waiting for client times...")
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((HOST, PORT))
    server.listen(NUM_CLIENTS)
    threads = []
    for _ in range(NUM_CLIENTS):
        conn, addr = server.accept()
        thread = threading.Thread(target=handle_client, args=(conn, addr))
        thread.start()
        threads.append(thread)
    for t in threads:
        t.join()
    print("All clients responded. Sending adjustments...")
    send_adjustments()
    server.close()

if __name__ == '__main__':
    main()
