import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Desktop;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Calendar;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.KeyAdapter;


public class Fantasy {

	private JFrame frame;
	private JTextField txtDays;
	private JTextField txtUrl;
	private JButton btnSubmit;
	int days;
	private JLabel lblError1;
	private int year = Calendar.getInstance().get(Calendar.YEAR);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fantasy window = new Fantasy();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Fantasy() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtDays = new JTextField();
		txtDays.setToolTipText("Enter between 2-7 days");
		txtDays.setHorizontalAlignment(SwingConstants.CENTER);
		txtDays.setBounds(145, 164, 156, 28);
		txtDays.setText("# Days (2-7)");
		frame.getContentPane().add(txtDays);
		txtDays.setColumns(10);
		
		final JLabel lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(26, 234, 121, 28);
		frame.getContentPane().add(lblError);
		
		txtUrl = new JTextField();
		txtUrl.setToolTipText("Enter \"Start Active Players\" URL");
		txtUrl.setHorizontalAlignment(SwingConstants.CENTER);
		txtUrl.setBounds(16, 193, 428, 28);
		txtUrl.setText("URL");
		frame.getContentPane().add(txtUrl);
		txtUrl.setColumns(10);
		
		btnSubmit = new JButton();
		btnSubmit.setIcon(new ImageIcon(Fantasy.class.getResource("/img/submit.png")));
		btnSubmit.setBounds(191, 231, 76, 31);
		btnSubmit.setBorder(null);
		
		btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnSubmit.setIcon(new ImageIcon(Fantasy.class.getResource("/img/submit-clkd.png")));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
				btnSubmit.setIcon(new ImageIcon(Fantasy.class.getResource("/img/submit.png")));
				btnSubmit.setBorder(null);
		    }
		});

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long startTime = System.nanoTime();
				String urlString = txtUrl.getText();
				boolean isInvalid = false;
				boolean isValidURL = false;
				try{
					days = Integer.parseInt(txtDays.getText());
					System.out.println(days);
					lblError.setText("");
				} catch (NumberFormatException nfe){
					lblError.setText("Invalid days!");
					isInvalid = true;
					}
				if(urlString.contains("https://basketball.fantasysports.yahoo.com/nba/") &&
						(days>=2) && (days<=7)){
					isValidURL = true;
					int start = urlString.indexOf(Integer.toString(year)); //index of date start
					String date = urlString.substring(start,start+10);
					String oldDate = "";
				for(int i = 0; i < days; i++){
							oldDate = date;
							date = nextDate(date);
						//	System.out.println(urlString);
					    try {
					        Desktop.getDesktop().browse(new URL(urlString).toURI());
							urlString = urlString.replace(oldDate, date);
					    } catch (Exception ex) {
					        ex.printStackTrace();
					    }
					}
				if(lblError1.getText().length()>0) lblError1.setText(""); //if previous error message still exists, remove it
				if(lblError.getText().length()>0) lblError.setText("");
				}else{
					if(!urlString.contains("https://basketball.fantasysports.yahoo.com/nba")) 
						lblError1.setText("Invalid URL!");
					if(days < 2 && isInvalid==false) lblError.setText("Pick more days!");
					else if(days > 7 && isInvalid==false) lblError.setText("Pick fewer days!");
				}

				txtDays.setText("");
				txtUrl.setText("");
				long stopTime = System.nanoTime();
				double result = (stopTime - startTime)/1000000;
				if(lblError1.getText().length()==0 && result > 100 && isValidURL == true) lblError1.setText("Done in "+result/1000+" s");
				else if(lblError1.getText().length()==0 && result < 100 && isValidURL == true) lblError1.setText("Done in "+result+" ms");
			}
		});
		frame.getContentPane().add(btnSubmit);
		KeyAdapter Enter = new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					btnSubmit.doClick();
				}
			}
		};
		txtUrl.addKeyListener(Enter);
		txtDays.addKeyListener(Enter);
		btnSubmit.addKeyListener(Enter);
		
		JLabel lblStepGo = new JLabel("");
		lblStepGo.setHorizontalAlignment(SwingConstants.CENTER);
		lblStepGo.setIcon(new ImageIcon(Fantasy.class.getResource("/img/instructions.png")));
		lblStepGo.setBounds(16, 49, 428, 114);
		frame.getContentPane().add(lblStepGo);
		
		JLabel lblYahooFantasyLineup = new JLabel("Yahoo! Fantasy Lineup Sorter");
		lblYahooFantasyLineup.setHorizontalAlignment(SwingConstants.CENTER);
		lblYahooFantasyLineup.setFont(new Font("Orator Std", Font.PLAIN, 18));
		lblYahooFantasyLineup.setBounds(16, 16, 398, 40);
		frame.getContentPane().add(lblYahooFantasyLineup);
		
		lblError1 = new JLabel("");
		lblError1.setHorizontalAlignment(SwingConstants.CENTER);
		lblError1.setForeground(Color.RED);
		lblError1.setBounds(312, 234, 121, 28);
		frame.getContentPane().add(lblError1);
		
		JLabel lblDays = new JLabel("");
		lblDays.setBounds(313, 170, 120, 16);
		frame.getContentPane().add(lblDays);

	}
/**
 * 
 * @param date the input date in YYYY-MM-DD format
 * @return the next day's date in the same format
 */
	
	public String nextDate(String date){
		int MMrange = 30;
		
		String result = "";	
		String daystr = date.substring(8,10);
		String monthstr = date.substring(5,7);

		int day = Integer.parseInt(daystr);
		int month = Integer.parseInt(monthstr);
		int year = Integer.parseInt(date.substring(0,4));
		if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) MMrange = 31;
		else if(month==2) MMrange = 28;
		if(year%4==0&&month==2) MMrange = 29;
		if(day==MMrange){
			day =1;
			month++;
		}else if(month==12&&day==31){
			year++;
			month = 1;
			day = 1;
		}else{
			day++;
		}
		result = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
		if(month <=9&&day<=9) result = Integer.toString(year)+"-0"+Integer.toString(month)+"-0"+Integer.toString(day);
		else if(month <= 9) result = Integer.toString(year)+"-0"+Integer.toString(month)+"-"+Integer.toString(day);
		else if(day <= 9) result = Integer.toString(year)+"-"+Integer.toString(month)+"-0"+Integer.toString(day);
		return result;
	}
}
