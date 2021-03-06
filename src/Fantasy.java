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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Fantasy {

	private JFrame frame;
	private JTextField txtUrl;
	private JButton btnSubmit;
	private JLabel lblError1;
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	private JLabel label;
	String invDays = "Invalid Days!";
	String invURL = "Invalid URL!";

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
	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JComboBox dayBox = new JComboBox();
		dayBox.setModel(new DefaultComboBoxModel(new String[] {"Days", "2", "3", "4", "5", "6", "7"}));
		dayBox.setBounds(183, 172, 87, 27);
		((JLabel)dayBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(dayBox);
		
		final JLabel lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(37, 238, 121, 28);
		frame.getContentPane().add(lblError);
		
		txtUrl = new JTextField();
		txtUrl.setToolTipText("Enter \"Start Active Players\" URL");
		txtUrl.setHorizontalAlignment(SwingConstants.CENTER);
		txtUrl.setBounds(16, 200, 428, 28);
		txtUrl.setText("URL");
		frame.getContentPane().add(txtUrl);
		txtUrl.setColumns(10);
		
		btnSubmit = new JButton();
		btnSubmit.setIcon(new ImageIcon(Fantasy.class.getResource("/img/submit.png")));
		btnSubmit.setBounds(188, 235, 76, 31);
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
				lblError.setText("");
				lblError1.setText("");
				int days = -1;
				long startTime = System.nanoTime();
				String urlString = txtUrl.getText();
				boolean isValidURL = false;
				try{
				days = Integer.parseInt((String) dayBox.getSelectedItem());
				if(lblError.getText().length()>0) lblError.setText("");
				} catch (NumberFormatException n){
					lblError.setText(invDays);	
				}
				if(!(lblError.getText().equals(invDays))){
				try{
	//https://basketball.fantasysports.yahoo.com/nba/105100/6/startactiveplayers?date=2016-02-17&crumb=NOiQuYBDyF2
				if(urlString.contains("https://basketball.fantasysports.yahoo.com/nba/")
						&& urlString.length()==107){ //url always 107 characters long
					isValidURL = true;
					int start = urlString.indexOf(Integer.toString(year)); //index of date start
					String date = urlString.substring(start,start+10);
					String oldDate = "";
				for(int i = 0; i < days; i++){
							oldDate = date;
							date = nextDate(date);
					    try {
					        Desktop.getDesktop().browse(new URL(urlString).toURI());
							urlString = urlString.replace(oldDate, date);
							System.out.println("success");
					    } catch (Exception ex) {
					        ex.printStackTrace();
					    }
					    try {
					    	  Thread.sleep(1500);
					    	} catch (InterruptedException ie) {
					    	    //Handle exception
					    	}
					}
				if(lblError1.getText().length()>0) lblError1.setText(""); //if previous error message still exists, remove it
				if(lblError.getText().length()>0) lblError.setText("");
				if(days > 0) days = 0;
					}else{
						lblError1.setText(invURL);
					}
				} 
				catch(NumberFormatException n){
					lblError.setText(invDays);
				}
				catch(Exception ex){
					//do Nothing
				}
				txtUrl.setText("");
				long stopTime = System.nanoTime();
				double result = (stopTime - startTime)/1000000;
				if(lblError1.getText().length()==0 && result > 100 && isValidURL == true) lblError1.setText("Done in "+result/1000+" s");
				else if(lblError1.getText().length()==0 && result < 100 && isValidURL == true) lblError1.setText("Done in "+result+" ms");
			}
				if(!(urlString.contains("https://basketball.fantasysports.yahoo.com/nba/"))
						&& urlString.length()!=107) lblError1.setText(invURL);
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
		btnSubmit.addKeyListener(Enter);
		
		JLabel lblYahooFantasyLineup = new JLabel("Yahoo! Fantasy Lineup Sorter");
		lblYahooFantasyLineup.setHorizontalAlignment(SwingConstants.CENTER);
		lblYahooFantasyLineup.setFont(new Font("Orator Std", Font.PLAIN, 18));
		lblYahooFantasyLineup.setBounds(16, 16, 398, 40);
		frame.getContentPane().add(lblYahooFantasyLineup);
		
		lblError1 = new JLabel("");
		lblError1.setHorizontalAlignment(SwingConstants.CENTER);
		lblError1.setForeground(Color.RED);
		lblError1.setBounds(293, 238, 121, 28);
		frame.getContentPane().add(lblError1);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(Fantasy.class.getResource("/img/instruc.png")));
		label.setBounds(16, 39, 438, 141);
		frame.getContentPane().add(label);

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
