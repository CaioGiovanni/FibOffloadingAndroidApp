package TCPpackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TCPserver extends JFrame{
	private JPanel contendPane;
	private static Socket s;
	private static ServerSocket ss;
	private static BufferedReader br;
	private static InputStreamReader isr;
	private static String message = "";
	
	static long fibonacci(long n)
	{
		if(n==1 || n==2)
		{
			return 1;
		}
		else
		{
			return fibonacci(n-1) + fibonacci(n-2);
		}	
	}
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TCPserver frame = new TCPserver();
					frame.setVisible(true);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		
		
		try {
			/*while (true) {
				ss = new ServerSocket(1000);
				System.out.println("Server running at port 1000");
				s = ss.accept();
				isr = new InputStreamReader(s.getInputStream());
				br = new BufferedReader(isr);
				message = br.readLine();
				
				//Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"ipconfig\""); 
				
				int messageConvertida = Integer.parseInt(message);
				// FAZ O FIB
				messageConvertida = fibonacci(messageConvertida);
				System.out.println(messageConvertida);
				
				isr.close();
				br.close();
				ss.close();
				s.close();
				
				s = new Socket("10.0.2.16",2000);
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				message = String.valueOf(messageConvertida);
				
				pw.write(message);
				pw.flush();
				
				pw.close();
				
				s.close();
				
			}*/
			DatagramSocket aScoket = null;
			try {
				aScoket = new DatagramSocket(1000);
				byte[] buffer = new byte[1000];
				while (true) {
					DatagramPacket request = new DatagramPacket(buffer, buffer.length);
					aScoket.receive(request);
					String messageRecived = new String(request.getData());
					String messageRecived2 = "";
					int i = 0;
					for (i = 0; messageRecived.charAt(i) != '\0'; i++) {
					}
					messageRecived2 = messageRecived.substring(0, i);
					System.out.println(messageRecived2);
					long j = Long.parseLong(messageRecived2);
					long messageConvertida = fibonacci(j);
					System.out.println(messageConvertida);
					String messageReply = String.valueOf(messageConvertida);
					byte[] buffer2 = messageReply.getBytes();
					
					DatagramPacket reply = new DatagramPacket(buffer2, buffer2.length, request.getAddress(), request.getPort());
					aScoket.send(reply);
				}
			} catch (Exception e) {
			}
			finally {
				if (aScoket != null) {
					aScoket.close();
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}
	
	
	public TCPserver() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		contendPane = new JPanel();
		contendPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contendPane);
		contendPane.setLayout(null);
		
		JLabel lblClient = new JLabel("SERVER");
		lblClient.setBounds(99,34,46,14);
		contendPane.add(lblClient);
	}
}
