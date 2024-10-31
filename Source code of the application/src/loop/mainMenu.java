/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

import java.awt.Image;
import java.sql.*;
import javax.swing.JOptionPane;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Frank
 */
public final class mainMenu extends javax.swing.JFrame {

    // global variables
    public Connection cn;
    public Statement st;
    public String filename = null;
    public int s = 0 ;
    public byte[] image_indentification= null;
    public String _username="";
    public String Searchby = "";
    public String selectedBookingIndex= "";
    public int mouseX  ;
    public int mouseY;
    public int x ;
    public int y;
    private boolean size = true;
   
    
    
    
    // get total & avaliable rooms and display them 
    public void get_totRooms()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date  = sdf.format(date_chooser_ava.getDate());
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        String Today = sdf.format(today);
        int booked_rooms=0;
        int tot_rom=0;
        try{
        String sql = "SELECT Count(`room_no`) FROM `each_room`";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {   
                tot_rom =Integer.parseInt(rs.getString("Count(`room_no`)"));
                totalRooms_textbox.setText(String.valueOf(tot_rom));
            }
        }
        catch(SQLException e )
        {
            JOptionPane.showMessageDialog(null,"not conected " );
        }
        int OS_rooms =0;
        try{
        String sql = "SELECT Count(`room_no`) FROM `each_room` Where`room_in_out_service` = 'Out of Service' ";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                OS_rooms = Integer.parseInt(rs.getString("Count(`room_no`)"));
                
            }
        }
        catch(SQLException e )
        {
            JOptionPane.showMessageDialog(null,"not conected " );
        }
        try
        {
            String sql = "SELECT * FROM `bookings` ";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                String datef = rs.getString("Booked_from");
                String datet = rs.getString("Booked_to");
                boolean ch1 = datef.compareTo(date) <= 0;
                boolean ch2 = datet.compareTo(date) >= 0;
                if(ch1 && ch2)
                {
                    String rooms = rs.getString("Booked_rooms");
                    int y = rooms.length();
                    // to get the tot number of rooms in booking
                    for(int z=0; z<y ; z++)
                    {
                        String t =rooms.substring(z,z+1);
                        if(t.equals(","))
                        {
                            booked_rooms++;
                        }
                    }
                }
                
            }
            int ava= 0 ;
            
            ava = tot_rom-(booked_rooms+OS_rooms);
           
            
            String aval = String.valueOf(ava);
            
            totalRoomsAvailable_textbox.setText(aval);
            
        }
         catch(SQLException e )
        {
            JOptionPane.showMessageDialog(null,"not conected " );
        }
    }
    
    
    
    // get each room type and set it in combobox
    public void RoomType_combobox()
    {
         try{
            String sql = "SELECT `Room_type` FROM `rooms`  ";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                RoomType_combobox.addItem(rs.getString("Room_type"));
                
            }
        }
        catch(SQLException e){ System.out.println(e);}
    }
    
    // get total rooms of each type and display them 
    public void eachRoomTypeAva(String Roomtype)
    {
        
        
         try{
            String sql = "SELECT Count(`room_no`) FROM `each_room` Where `room_type` ='"+Roomtype+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                totalRoomsOf_textbox.setText(rs.getString("Count(`room_no`)"));
                eachTRoomTypeAva2(Roomtype,Integer.parseInt(rs.getString("Count(`room_no`)")) );
                
            }
        }
        catch(SQLException e){ System.out.println("1");}
    }
    //gets avaliable rooms of each type 
    private void eachTRoomTypeAva2(String rt,int tot_rom)
    {
        
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date  = sdf.format(date_chooser_ava.getDate());
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        String Today = sdf.format(today);
        int booked_rooms=0;
        int OS_rooms= 0;
        
        // get all out of service rooms
         try{
            String sql = "SELECT Count(`room_no`) FROM `each_room` Where`room_in_out_service` = 'Out of Service' AND`room_type` ='"+rt+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                 OS_rooms = Integer.parseInt(rs.getString("Count(`room_no`)"));
                
            }
        }
         
        catch(SQLException e){ System.out.println("3");}
         // all the booking and send to find which one of them has a room booked of the roomtype we are right now searching of 
          try
        {
            String sql = "SELECT * FROM `bookings` ";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String allRooms= "";
            while(rs.next())
            {
                String datef = rs.getString("Booked_from");
                String datet = rs.getString("Booked_to");
                boolean ch1 = datef.compareTo(date) <= 0;
                boolean ch2 = datet.compareTo(date) >= 0;
                if(ch1 && ch2)
                {
                    allRooms = allRooms +rs.getString("Booked_rooms");
                }
                
            }
            find_room(tot_rom,OS_rooms,allRooms ,rt );
             
      
        }
         catch(SQLException e )
        {
            JOptionPane.showMessageDialog(null,"3 " );
        }
         
    }
    
    
    private void find_room(int tot_room, int out_of_service , String RM, String RT)
    {
       int num_words = 0;
        // array to store each room
        
                String t2 ="";
                if(RM.equals("")!= true)
                {
                int y = RM.length();
                // to get the tot number of rooms in booking
                for(int z=0; z<y ; z++)
                {
                    String t =RM.substring(z,z+1);
                    if(t.equals(","))
                    {
                        num_words++;
                    }
                }
                String[] list = new String[num_words];
                // array to store each room
                int ct = 0;
                //breaks all the roooms and puts them into the array
                for(int z=0; z<y ; z++)
                {
                    String t = RM.substring(z,z+1);
                    
                    if(t.equals(","))
                    {
                        list[ct] = t2;
                        ct++;
                        t2 ="";
                    }
                    else
                    {
                        t2 = t2+t ;
                    }
                }
                int bookedR= 0;
                
                for(int coc=0; coc<num_words; coc++ ){
                    try
                    {
                       String sql = "SELECT * FROM `each_room` Where `room_no` = '"+list[coc]+"'";
                       PreparedStatement ps = cn.prepareStatement(sql);
                       ResultSet rs = ps.executeQuery();
                       while(rs.next())
                       {
                           String room_t = rs.getString("room_type");
                           if(room_t.equals(RT)){bookedR++;}
                       }

                    }
                    catch(SQLException e){ System.out.println("3");}
                }
                
                int t = out_of_service+bookedR ;
                int ava = tot_room - t ;
                String aval = String.valueOf(ava);
                totalRoomsAvailableOf_textbox.setText(aval);
                
                
                }
                else 
                { 
                    totalRoomsAvailableOf_textbox.setText(String.valueOf(tot_room-out_of_service));
                }
                
    }
    
    
    
    
    
    // updates app the data according to the data entered in all the tables 
    public void update()
    {
        get_set_data pop = new get_set_data();
        pop.main(cn);
        
    }
    public void admin(String username )
    {
        try{
            String sql = "SELECT `authority` FROM `login_employee` WHERE `username` = '"+username+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                if (rs.getString("authority").equals("Admin_Access"))
                {
                    adminOptions_button.setVisible(true);
                }
                
            }
        }
        catch(SQLException e){ System.out.println(e);}
    }
    
    public mainMenu() {
        initComponents();
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        date_chooser_ava.setDate(today);
        date_chooser_ava.setMinSelectableDate(today);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        update();
        availability_pane.setVisible(true);
        booking_pane.setVisible(false);
        bookingLog_pane.setVisible(false);
        disable();
        get_totRooms();
        RoomType_combobox();
        adminOptions_button.setVisible(false);
        admin(_username);
        clear_bookingLog();
        for_all.doClick();
    }
    
    
    public mainMenu(String username, Connection con) {
        initComponents();
        cn = con;
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        date_chooser_ava.setDate(today);
        date_chooser_ava.setMinSelectableDate(today);
        update();
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        availability_pane.setVisible(true);
        booking_pane.setVisible(false);
        bookingLog_pane.setVisible(false);
        disable();
        get_totRooms();
        RoomType_combobox();
        _username = username;
        adminOptions_button.setVisible(false);
        admin(_username);
        clear_bookingLog();
        for_all.doClick();
    }
    
    
    public void disable()
    {
        /// error set disable in booking panel
        error1_lable_book.setVisible(false);
        error2_lable_book.setVisible(false);
        error3_lable_book.setVisible(false);
        error4_lable_book.setVisible(false);
        error5_lable_book.setVisible(false);
        error565_lable_book.setVisible(false);
       
        ///
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        dateFrom_datechooser.setMinSelectableDate(today);
        dateTo_datechooser.setMinSelectableDate(today);
        
        ///clear all fields in booking plane 
        firstName_textbox.setText("");
        lastName_textbox.setText("");
        dateFrom_datechooser.setDate(null);
        dateTo_datechooser.setDate(null);
        noFoAdults_textbox.setText("");
        noFoChildren_textbox.setText("");
       
    }
    
    public void clear_bookingLog()
    {
         DefaultTableModel model = (DefaultTableModel)bookingLog_table.getModel();
              while(model.getRowCount() > 0)
             {
              model.removeRow(0);
             }
        searchName_textbox.setText("");
        for_all.doClick();
        
        datechooser_Search.setDate(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SearchBy_bookinglog = new javax.swing.ButtonGroup();
        master_pane = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        style1_pane = new javax.swing.JPanel();
        hotel_lable = new javax.swing.JLabel();
        reservation_lable = new javax.swing.JLabel();
        avaliablity_button = new javax.swing.JButton();
        booking_button = new javax.swing.JButton();
        adminOptions_button = new javax.swing.JButton();
        adminOptions_button1 = new javax.swing.JButton();
        masterLayered_pane = new javax.swing.JLayeredPane();
        availability_pane = new javax.swing.JPanel();
        totalRoomsAvailable_textbox = new javax.swing.JTextField();
        totalRoomsAvailableOf_lable = new javax.swing.JLabel();
        totalRoomsAvailable_lable = new javax.swing.JLabel();
        error1_lable = new javax.swing.JLabel();
        error2_lable = new javax.swing.JLabel();
        totalRooms_textbox = new javax.swing.JTextField();
        check_button = new javax.swing.JButton();
        style1_lable = new javax.swing.JLabel();
        totalRoomsOf_textbox = new javax.swing.JTextField();
        style2_lable = new javax.swing.JLabel();
        totalRoomsAvailableOf_textbox = new javax.swing.JTextField();
        RoomType_combobox = new javax.swing.JComboBox<>();
        date_chooser_ava = new com.toedter.calendar.JDateChooser();
        totalRoomsAvailable_lable1 = new javax.swing.JLabel();
        booking_pane = new javax.swing.JPanel();
        firstName_lable = new javax.swing.JLabel();
        firstName_textbox = new javax.swing.JTextField();
        lastName_textbox = new javax.swing.JTextField();
        noFoAdults_label = new javax.swing.JLabel();
        noFoAdults_textbox = new javax.swing.JTextField();
        noFoChildren_label = new javax.swing.JLabel();
        noFoChildren_textbox = new javax.swing.JTextField();
        bookConfirm_button = new javax.swing.JButton();
        dateFrom_datechooser = new com.toedter.calendar.JDateChooser();
        dateFrom_label = new javax.swing.JLabel();
        dateTo_label = new javax.swing.JLabel();
        dateTo_datechooser = new com.toedter.calendar.JDateChooser();
        error5_lable_book = new javax.swing.JLabel();
        error1_lable_book = new javax.swing.JLabel();
        error2_lable_book = new javax.swing.JLabel();
        error3_lable_book = new javax.swing.JLabel();
        error4_lable_book = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();
        lastName_lable = new javax.swing.JLabel();
        identificationNo_lable = new javax.swing.JLabel();
        addIdentification_button = new javax.swing.JButton();
        error565_lable_book = new javax.swing.JLabel();
        bookingLog_pane = new javax.swing.JPanel();
        firstName_lable3 = new javax.swing.JLabel();
        searchName_textbox = new javax.swing.JTextField();
        deleteSelectedBooking_button = new javax.swing.JButton();
        search_firstname = new javax.swing.JRadioButton();
        datechooser_Search = new com.toedter.calendar.JDateChooser();
        search_lastname = new javax.swing.JRadioButton();
        search_date = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookingLog_table = new javax.swing.JTable();
        search_button = new javax.swing.JButton();
        firstName_lable1 = new javax.swing.JLabel();
        editSelectedBooking_button = new javax.swing.JButton();
        firstName_lable2 = new javax.swing.JLabel();
        selectedRoom_textBox = new javax.swing.JTextField();
        for_all = new javax.swing.JRadioButton();
        logOut_Button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        closeButton1 = new javax.swing.JButton();
        closeButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        master_pane.setBackground(new java.awt.Color(255, 255, 255));
        master_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                master_paneMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                master_paneMousePressed(evt);
            }
        });

        closeButton.setBackground(new java.awt.Color(0, 102, 255));
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("X");
        closeButton.setToolTipText("");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        style1_pane.setBackground(new java.awt.Color(51, 102, 255));

        hotel_lable.setBackground(new java.awt.Color(0, 204, 255));
        hotel_lable.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 36)); // NOI18N
        hotel_lable.setForeground(new java.awt.Color(255, 255, 255));
        hotel_lable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hotel_lable.setText("Reservation");
        hotel_lable.setToolTipText("");

        reservation_lable.setBackground(new java.awt.Color(0, 204, 255));
        reservation_lable.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 36)); // NOI18N
        reservation_lable.setForeground(new java.awt.Color(255, 255, 255));
        reservation_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        reservation_lable.setText("Hotel ");
        reservation_lable.setToolTipText("");

        avaliablity_button.setBackground(new java.awt.Color(255, 255, 255));
        avaliablity_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        avaliablity_button.setForeground(new java.awt.Color(51, 102, 255));
        avaliablity_button.setText("Availability");
        avaliablity_button.setToolTipText("");
        avaliablity_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avaliablity_buttonActionPerformed(evt);
            }
        });

        booking_button.setBackground(new java.awt.Color(255, 255, 255));
        booking_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        booking_button.setForeground(new java.awt.Color(51, 102, 255));
        booking_button.setText("Booking");
        booking_button.setToolTipText("");
        booking_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booking_buttonActionPerformed(evt);
            }
        });

        adminOptions_button.setBackground(new java.awt.Color(255, 255, 255));
        adminOptions_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        adminOptions_button.setForeground(new java.awt.Color(51, 102, 255));
        adminOptions_button.setText("Admin Options");
        adminOptions_button.setToolTipText("");
        adminOptions_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminOptions_buttonActionPerformed(evt);
            }
        });

        adminOptions_button1.setBackground(new java.awt.Color(255, 255, 255));
        adminOptions_button1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        adminOptions_button1.setForeground(new java.awt.Color(51, 102, 255));
        adminOptions_button1.setText("Booking log");
        adminOptions_button1.setToolTipText("");
        adminOptions_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminOptions_button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout style1_paneLayout = new javax.swing.GroupLayout(style1_pane);
        style1_pane.setLayout(style1_paneLayout);
        style1_paneLayout.setHorizontalGroup(
            style1_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(style1_paneLayout.createSequentialGroup()
                .addGroup(style1_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hotel_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, style1_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(reservation_lable)
                        .addGap(50, 50, 50)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(style1_paneLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(style1_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminOptions_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminOptions_button, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(booking_button, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avaliablity_button, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        style1_paneLayout.setVerticalGroup(
            style1_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(style1_paneLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(reservation_lable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hotel_lable)
                .addGap(56, 56, 56)
                .addComponent(avaliablity_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(booking_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(adminOptions_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(adminOptions_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        masterLayered_pane.setPreferredSize(new java.awt.Dimension(800, 0));

        availability_pane.setBackground(new java.awt.Color(255, 255, 255));
        availability_pane.setForeground(new java.awt.Color(255, 255, 255));

        totalRoomsAvailable_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalRoomsAvailable_textbox.setEnabled(false);
        totalRoomsAvailable_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                totalRoomsAvailable_textboxMouseClicked(evt);
            }
        });
        totalRoomsAvailable_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalRoomsAvailable_textboxActionPerformed(evt);
            }
        });

        totalRoomsAvailableOf_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalRoomsAvailableOf_lable.setForeground(new java.awt.Color(51, 102, 255));
        totalRoomsAvailableOf_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalRoomsAvailableOf_lable.setText("Total Rooms Available of ");

        totalRoomsAvailable_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalRoomsAvailable_lable.setForeground(new java.awt.Color(51, 102, 255));
        totalRoomsAvailable_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalRoomsAvailable_lable.setText("Date of Avaliability");

        error1_lable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        error1_lable.setForeground(new java.awt.Color(204, 0, 0));

        error2_lable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        error2_lable.setForeground(new java.awt.Color(204, 0, 0));

        totalRooms_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalRooms_textbox.setEnabled(false);
        totalRooms_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                totalRooms_textboxMouseClicked(evt);
            }
        });

        check_button.setBackground(new java.awt.Color(51, 102, 255));
        check_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        check_button.setForeground(new java.awt.Color(255, 255, 255));
        check_button.setText("Check");
        check_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_buttonActionPerformed(evt);
            }
        });

        style1_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        style1_lable.setForeground(new java.awt.Color(51, 102, 255));
        style1_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        style1_lable.setText("/");

        totalRoomsOf_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalRoomsOf_textbox.setEnabled(false);
        totalRoomsOf_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                totalRoomsOf_textboxMouseClicked(evt);
            }
        });

        style2_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        style2_lable.setForeground(new java.awt.Color(51, 102, 255));
        style2_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        style2_lable.setText("/");

        totalRoomsAvailableOf_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalRoomsAvailableOf_textbox.setEnabled(false);
        totalRoomsAvailableOf_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                totalRoomsAvailableOf_textboxMouseClicked(evt);
            }
        });

        RoomType_combobox.setBackground(new java.awt.Color(51, 102, 255));
        RoomType_combobox.setForeground(new java.awt.Color(255, 255, 255));
        RoomType_combobox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RoomType_comboboxMouseClicked(evt);
            }
        });
        RoomType_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoomType_comboboxActionPerformed(evt);
            }
        });

        date_chooser_ava.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                date_chooser_avaMouseClicked(evt);
            }
        });

        totalRoomsAvailable_lable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        totalRoomsAvailable_lable1.setForeground(new java.awt.Color(51, 102, 255));
        totalRoomsAvailable_lable1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalRoomsAvailable_lable1.setText("Total Rooms Available ");

        javax.swing.GroupLayout availability_paneLayout = new javax.swing.GroupLayout(availability_pane);
        availability_pane.setLayout(availability_paneLayout);
        availability_paneLayout.setHorizontalGroup(
            availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(availability_paneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, availability_paneLayout.createSequentialGroup()
                        .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(availability_paneLayout.createSequentialGroup()
                                .addComponent(totalRoomsAvailableOf_lable)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RoomType_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(check_button, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(availability_paneLayout.createSequentialGroup()
                                    .addComponent(totalRoomsAvailableOf_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(style2_lable)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(totalRoomsOf_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, availability_paneLayout.createSequentialGroup()
                        .addComponent(totalRoomsAvailable_lable)
                        .addGap(18, 18, 18)
                        .addComponent(date_chooser_ava, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(availability_paneLayout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(totalRoomsAvailable_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(style1_lable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totalRooms_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(error1_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(error2_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(389, 389, 389))
            .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(availability_paneLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(totalRoomsAvailable_lable1)
                    .addContainerGap(718, Short.MAX_VALUE)))
        );
        availability_paneLayout.setVerticalGroup(
            availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(availability_paneLayout.createSequentialGroup()
                .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(availability_paneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(error2_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(error1_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(availability_paneLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(date_chooser_ava, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalRoomsAvailable_lable))
                        .addGap(14, 14, 14)
                        .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalRoomsAvailable_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(style1_lable)
                            .addComponent(totalRooms_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(availability_paneLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(totalRoomsAvailableOf_lable))
                            .addComponent(RoomType_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalRoomsAvailableOf_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(style2_lable)
                            .addComponent(totalRoomsOf_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31)
                .addComponent(check_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(237, Short.MAX_VALUE))
            .addGroup(availability_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(availability_paneLayout.createSequentialGroup()
                    .addGap(133, 133, 133)
                    .addComponent(totalRoomsAvailable_lable1)
                    .addContainerGap(497, Short.MAX_VALUE)))
        );

        booking_pane.setBackground(new java.awt.Color(255, 255, 255));
        booking_pane.setForeground(new java.awt.Color(255, 255, 255));

        firstName_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstName_lable.setForeground(new java.awt.Color(51, 102, 255));
        firstName_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstName_lable.setText(" First Name ");

        firstName_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstName_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firstName_textboxMouseClicked(evt);
            }
        });
        firstName_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstName_textboxActionPerformed(evt);
            }
        });

        lastName_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lastName_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lastName_textboxMouseClicked(evt);
            }
        });

        noFoAdults_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noFoAdults_label.setForeground(new java.awt.Color(51, 102, 255));
        noFoAdults_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        noFoAdults_label.setText("No.of Adults ");

        noFoAdults_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noFoAdults_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noFoAdults_textboxMouseClicked(evt);
            }
        });
        noFoAdults_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noFoAdults_textboxActionPerformed(evt);
            }
        });

        noFoChildren_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noFoChildren_label.setForeground(new java.awt.Color(51, 102, 255));
        noFoChildren_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        noFoChildren_label.setText("No.of  Children ");

        noFoChildren_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noFoChildren_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noFoChildren_textboxMouseClicked(evt);
            }
        });

        bookConfirm_button.setBackground(new java.awt.Color(51, 102, 255));
        bookConfirm_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        bookConfirm_button.setForeground(new java.awt.Color(255, 255, 255));
        bookConfirm_button.setText("Book");
        bookConfirm_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookConfirm_buttonActionPerformed(evt);
            }
        });

        dateFrom_datechooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateFrom_datechooserMouseClicked(evt);
            }
        });

        dateFrom_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dateFrom_label.setForeground(new java.awt.Color(51, 102, 255));
        dateFrom_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateFrom_label.setText("Date From");
        dateFrom_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateFrom_labelMouseClicked(evt);
            }
        });

        dateTo_label.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dateTo_label.setForeground(new java.awt.Color(51, 102, 255));
        dateTo_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateTo_label.setText("Date To ");
        dateTo_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateTo_labelMouseClicked(evt);
            }
        });

        dateTo_datechooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateTo_datechooserMouseClicked(evt);
            }
        });

        error5_lable_book.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error5_lable_book.setForeground(new java.awt.Color(204, 0, 0));
        error5_lable_book.setText("Enter the vaild dates ");

        error1_lable_book.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error1_lable_book.setForeground(new java.awt.Color(255, 0, 0));
        error1_lable_book.setText("*");

        error2_lable_book.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error2_lable_book.setForeground(new java.awt.Color(255, 0, 0));
        error2_lable_book.setText("*");

        error3_lable_book.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error3_lable_book.setForeground(new java.awt.Color(255, 0, 0));
        error3_lable_book.setText("*");

        error4_lable_book.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error4_lable_book.setForeground(new java.awt.Color(255, 0, 0));
        error4_lable_book.setText("*");

        icon.setPreferredSize(new java.awt.Dimension(359, 270));

        lastName_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lastName_lable.setForeground(new java.awt.Color(51, 102, 255));
        lastName_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lastName_lable.setText("Last Name  ");

        identificationNo_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        identificationNo_lable.setForeground(new java.awt.Color(51, 102, 255));
        identificationNo_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        identificationNo_lable.setText("Add Identification");

        addIdentification_button.setBackground(new java.awt.Color(51, 102, 255));
        addIdentification_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        addIdentification_button.setForeground(new java.awt.Color(255, 255, 255));
        addIdentification_button.setText("Add");
        addIdentification_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIdentification_buttonActionPerformed(evt);
            }
        });

        error565_lable_book.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        error565_lable_book.setForeground(new java.awt.Color(255, 0, 0));
        error565_lable_book.setText("*Enter Indentification*");

        javax.swing.GroupLayout booking_paneLayout = new javax.swing.GroupLayout(booking_pane);
        booking_pane.setLayout(booking_paneLayout);
        booking_paneLayout.setHorizontalGroup(
            booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(booking_paneLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lastName_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstName_lable))
                .addGap(18, 18, 18)
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(firstName_textbox, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                    .addComponent(lastName_textbox))
                .addGap(61, 61, 61)
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(error1_lable_book)
                    .addComponent(error2_lable_book))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(booking_paneLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateFrom_label)
                    .addComponent(dateTo_label))
                .addGap(18, 18, 18)
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateTo_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addComponent(dateFrom_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(error5_lable_book)))
                .addGap(0, 22, Short.MAX_VALUE))
            .addGroup(booking_paneLayout.createSequentialGroup()
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(noFoAdults_label)
                            .addComponent(noFoChildren_label))
                        .addGap(18, 18, 18)
                        .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(booking_paneLayout.createSequentialGroup()
                                .addComponent(noFoChildren_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(error4_lable_book))
                            .addGroup(booking_paneLayout.createSequentialGroup()
                                .addComponent(noFoAdults_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(error3_lable_book))))
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(identificationNo_lable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addIdentification_button, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(error565_lable_book)))
                .addGap(18, 18, 18)
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(bookConfirm_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        booking_paneLayout.setVerticalGroup(
            booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(booking_paneLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(error1_lable_book))
                    .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(firstName_lable)
                        .addComponent(firstName_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastName_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastName_lable)))
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(error2_lable_book)))
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(dateFrom_label))
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(error5_lable_book, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateFrom_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21)
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateTo_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(dateTo_label)))
                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bookConfirm_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(booking_paneLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(noFoAdults_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(noFoAdults_label)
                            .addComponent(error3_lable_book))
                        .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(booking_paneLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(error4_lable_book))
                            .addGroup(booking_paneLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(noFoChildren_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(noFoChildren_label))))
                        .addGap(36, 36, 36)
                        .addGroup(booking_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(identificationNo_lable)
                            .addComponent(addIdentification_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(error565_lable_book)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bookingLog_pane.setBackground(new java.awt.Color(255, 255, 255));
        bookingLog_pane.setForeground(new java.awt.Color(255, 255, 255));

        firstName_lable3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstName_lable3.setForeground(new java.awt.Color(51, 102, 255));
        firstName_lable3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstName_lable3.setText(" Name");

        searchName_textbox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        searchName_textbox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchName_textboxMouseClicked(evt);
            }
        });
        searchName_textbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchName_textboxActionPerformed(evt);
            }
        });

        deleteSelectedBooking_button.setBackground(new java.awt.Color(204, 0, 0));
        deleteSelectedBooking_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        deleteSelectedBooking_button.setForeground(new java.awt.Color(255, 255, 255));
        deleteSelectedBooking_button.setText("Delete");
        deleteSelectedBooking_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedBooking_buttonActionPerformed(evt);
            }
        });

        SearchBy_bookinglog.add(search_firstname);
        search_firstname.setText("Search By First name");
        search_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_firstnameActionPerformed(evt);
            }
        });

        datechooser_Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_SearchMouseClicked(evt);
            }
        });

        SearchBy_bookinglog.add(search_lastname);
        search_lastname.setText("Search By last name");
        search_lastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_lastnameActionPerformed(evt);
            }
        });

        SearchBy_bookinglog.add(search_date);
        search_date.setText("Search By Date ");
        search_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_dateActionPerformed(evt);
            }
        });

        bookingLog_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index No.", "First Name", "last Name", "Booked From", "Booked To", "Booked Rooms", "Total Cost ", "No. Children", "No. Adults "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        bookingLog_table.setRowHeight(20);
        bookingLog_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingLog_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bookingLog_table);

        search_button.setBackground(new java.awt.Color(51, 102, 255));
        search_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        search_button.setForeground(new java.awt.Color(255, 255, 255));
        search_button.setText("Search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        firstName_lable1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstName_lable1.setForeground(new java.awt.Color(51, 102, 255));
        firstName_lable1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstName_lable1.setText("Date ");

        editSelectedBooking_button.setBackground(new java.awt.Color(51, 102, 255));
        editSelectedBooking_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        editSelectedBooking_button.setForeground(new java.awt.Color(255, 255, 255));
        editSelectedBooking_button.setText("Edit");
        editSelectedBooking_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSelectedBooking_buttonActionPerformed(evt);
            }
        });

        firstName_lable2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        firstName_lable2.setForeground(new java.awt.Color(51, 102, 255));
        firstName_lable2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstName_lable2.setText("Ind No.");

        selectedRoom_textBox.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        selectedRoom_textBox.setDisabledTextColor(new java.awt.Color(51, 102, 255));
        selectedRoom_textBox.setEnabled(false);
        selectedRoom_textBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedRoom_textBoxMouseClicked(evt);
            }
        });
        selectedRoom_textBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectedRoom_textBoxActionPerformed(evt);
            }
        });

        SearchBy_bookinglog.add(for_all);
        for_all.setText("Search For All");
        for_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                for_allActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bookingLog_paneLayout = new javax.swing.GroupLayout(bookingLog_pane);
        bookingLog_pane.setLayout(bookingLog_paneLayout);
        bookingLog_paneLayout.setHorizontalGroup(
            bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingLog_paneLayout.createSequentialGroup()
                .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingLog_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(firstName_lable2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selectedRoom_textBox)
                        .addGap(40, 40, 40)
                        .addComponent(editSelectedBooking_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteSelectedBooking_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bookingLog_paneLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(firstName_lable3)
                            .addComponent(firstName_lable1))
                        .addGap(18, 18, 18)
                        .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(datechooser_Search, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(searchName_textbox))
                        .addGap(32, 32, 32)
                        .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(search_firstname)
                            .addComponent(search_date)
                            .addComponent(search_lastname))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(for_all)))
                    .addGroup(bookingLog_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        bookingLog_paneLayout.setVerticalGroup(
            bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingLog_paneLayout.createSequentialGroup()
                .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingLog_paneLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchName_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstName_lable3))
                        .addGap(18, 18, 18)
                        .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(datechooser_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingLog_paneLayout.createSequentialGroup()
                                .addComponent(firstName_lable1)
                                .addGap(9, 9, 9))))
                    .addGroup(bookingLog_paneLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(search_firstname)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(search_lastname)
                        .addGap(20, 20, 20)
                        .addComponent(search_date))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingLog_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(for_all)
                        .addGap(18, 18, 18)
                        .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(bookingLog_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteSelectedBooking_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editSelectedBooking_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstName_lable2)
                    .addComponent(selectedRoom_textBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        masterLayered_pane.setLayer(availability_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        masterLayered_pane.setLayer(booking_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        masterLayered_pane.setLayer(bookingLog_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout masterLayered_paneLayout = new javax.swing.GroupLayout(masterLayered_pane);
        masterLayered_pane.setLayout(masterLayered_paneLayout);
        masterLayered_paneLayout.setHorizontalGroup(
            masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(masterLayered_paneLayout.createSequentialGroup()
                .addComponent(availability_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addComponent(bookingLog_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 60, Short.MAX_VALUE)))
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addComponent(booking_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 20, Short.MAX_VALUE)))
        );
        masterLayered_paneLayout.setVerticalGroup(
            masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(availability_pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(bookingLog_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(6, 6, 6)))
            .addGroup(masterLayered_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(masterLayered_paneLayout.createSequentialGroup()
                    .addComponent(booking_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        logOut_Button.setBackground(new java.awt.Color(0, 102, 255));
        logOut_Button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        logOut_Button.setForeground(new java.awt.Color(255, 255, 255));
        logOut_Button.setText("Log out");
        logOut_Button.setToolTipText("");
        logOut_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOut_ButtonActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });

        closeButton1.setBackground(new java.awt.Color(0, 102, 255));
        closeButton1.setForeground(new java.awt.Color(255, 255, 255));
        closeButton1.setText("-");
        closeButton1.setToolTipText("");
        closeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButton1ActionPerformed(evt);
            }
        });

        closeButton2.setBackground(new java.awt.Color(0, 102, 255));
        closeButton2.setForeground(new java.awt.Color(255, 255, 255));
        closeButton2.setText("=");
        closeButton2.setToolTipText("");
        closeButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout master_paneLayout = new javax.swing.GroupLayout(master_pane);
        master_pane.setLayout(master_paneLayout);
        master_paneLayout.setHorizontalGroup(
            master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(master_paneLayout.createSequentialGroup()
                .addComponent(style1_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(master_paneLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(logOut_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, master_paneLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(masterLayered_pane, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE))))
        );
        master_paneLayout.setVerticalGroup(
            master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(style1_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(master_paneLayout.createSequentialGroup()
                .addGroup(master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addGroup(master_paneLayout.createSequentialGroup()
                        .addGroup(master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(logOut_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(masterLayered_pane, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(master_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(master_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void avaliablity_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avaliablity_buttonActionPerformed
        // TODO add your handling code here:
         availability_pane.setVisible(true);
        booking_pane.setVisible(false);
        bookingLog_pane.setVisible(false);
        update();
        disable();
        for_all.doClick();
        clear_bookingLog();
        get_totRooms();
    }//GEN-LAST:event_avaliablity_buttonActionPerformed

    private void booking_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booking_buttonActionPerformed
        // TODO add your handling code here:
        availability_pane.setVisible(false);
        booking_pane.setVisible(true);
        bookingLog_pane.setVisible(false);
        for_all.doClick();
        clear_bookingLog();
    }//GEN-LAST:event_booking_buttonActionPerformed

    private void adminOptions_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminOptions_buttonActionPerformed
        // TODO add your handling code here:
        new admin_options(_username, cn).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_adminOptions_buttonActionPerformed

    private void adminOptions_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminOptions_button1ActionPerformed
        // TODO add your handling code here:
        availability_pane.setVisible(false);
        booking_pane.setVisible(false);
        bookingLog_pane.setVisible(true);
        disable();
    }//GEN-LAST:event_adminOptions_button1ActionPerformed

    private void totalRoomsAvailable_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalRoomsAvailable_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_totalRoomsAvailable_textboxMouseClicked

    private void totalRooms_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalRooms_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_totalRooms_textboxMouseClicked

    private void check_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_buttonActionPerformed
        // TODO add your handling code here:
        get_totRooms();
        String SelectedRoomType = RoomType_combobox.getSelectedItem().toString();
        eachRoomTypeAva(SelectedRoomType);
    }//GEN-LAST:event_check_buttonActionPerformed

    private void totalRoomsOf_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalRoomsOf_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_totalRoomsOf_textboxMouseClicked

    private void totalRoomsAvailableOf_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalRoomsAvailableOf_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_totalRoomsAvailableOf_textboxMouseClicked

    private void RoomType_comboboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RoomType_comboboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_RoomType_comboboxMouseClicked

    private void RoomType_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoomType_comboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RoomType_comboboxActionPerformed

    private void logOut_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOut_ButtonActionPerformed
        // TODO add your handling code here:
        new login().setVisible(true);
        this.dispose();
        _username="";
    }//GEN-LAST:event_logOut_ButtonActionPerformed

    private void searchName_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchName_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_searchName_textboxMouseClicked

    private void searchName_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchName_textboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchName_textboxActionPerformed

    private void deleteSelectedBooking_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSelectedBooking_buttonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)bookingLog_table.getModel();
        int select = bookingLog_table.getSelectedRow();
        selectedBookingIndex =model.getValueAt(select,0).toString();
        new Booking_delete_confirm(selectedBookingIndex, cn).setVisible(true);
        selectedRoom_textBox.setText("");
        
         
            while(model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
        
    }//GEN-LAST:event_deleteSelectedBooking_buttonActionPerformed

    private void search_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_firstnameActionPerformed
        // TODO add your handling code here:
        Searchby = "FirstName";
    }//GEN-LAST:event_search_firstnameActionPerformed

    private void datechooser_SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_SearchMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_SearchMouseClicked

    private void search_lastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_lastnameActionPerformed
        // TODO add your handling code here:
        Searchby = "LastName";
    }//GEN-LAST:event_search_lastnameActionPerformed

    private void search_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_dateActionPerformed
        // TODO add your handling code here:
        Searchby = "Date";
    }//GEN-LAST:event_search_dateActionPerformed

    private void bookingLog_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingLog_tableMouseClicked
        // TODO add your handling code here:
        
        
        //get the selected row 
        DefaultTableModel model = (DefaultTableModel)bookingLog_table.getModel();
        int select = bookingLog_table.getSelectedRow();
        selectedBookingIndex =model.getValueAt(select,0).toString();
        selectedRoom_textBox.setText(selectedBookingIndex);
        
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_bookingLog_tableMouseClicked

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        // TODO add your handling code here:
        //get all the records
        if (Searchby.equals(""))
        {
            DefaultTableModel model = (DefaultTableModel)bookingLog_table.getModel();
            while(model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
            try{
                String sql = ("SELECT * FROM `bookings` ");
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {

                    String firstname = rs.getString("Booked_by_firstname");
                    String lastname = rs.getString("Booked_by_lastname");
                    String bookedFrom = rs.getString("Booked_from");
                    String bookedTo = rs.getString("Booked_to");
                    String bookedRooms = rs.getString("Booked_rooms");
                    String totcost = rs.getString("Total_cost");
                    String num_children = rs.getString("No. of Children");
                    String num_adult = rs.getString("No. of Adults");
                    String bookingInd = String.valueOf(rs.getInt("Ind_book"));
                    model.addRow(new Object [] {bookingInd ,firstname,lastname,bookedFrom,bookedTo,bookedRooms,totcost,num_children,num_adult});
                    datechooser_Search.setDate(null);
                    searchName_textbox.setText(null);
                    

                }
            }
            catch(SQLException e){ System.out.println(e);}
        }

        // get records by firstname
        if (Searchby.equals("FirstName"))
        {
            DefaultTableModel model = (DefaultTableModel)bookingLog_table.getModel();
            while(model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
            String name = searchName_textbox.getText();
            try{
                String sql = ("SELECT * FROM `bookings` WHERE `Booked_by_firstname` ='"+name+"'");
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {

                    
                    String firstname = rs.getString("Booked_by_firstname");
                    String lastname = rs.getString("Booked_by_lastname");
                    String bookedFrom = rs.getString("Booked_from");
                    String bookedTo = rs.getString("Booked_to");
                    String bookedRooms = rs.getString("Booked_rooms");
                    String totcost = rs.getString("Total_cost");
                    String num_children = rs.getString("No. of Children");
                    String num_adult = rs.getString("No. of Adults");
                    String bookingInd = String.valueOf(rs.getInt("Ind_book"));
                    model.addRow(new Object [] {bookingInd ,firstname,lastname,bookedFrom,bookedTo,bookedRooms,totcost,num_children,num_adult});
                    datechooser_Search.setDate(null);
                    

                }
            }
            catch(SQLException e){ System.out.println(e);}
        }

        // get records by lasttname
        if (Searchby.equals("LastName"))
        {
            DefaultTableModel model = (DefaultTableModel)bookingLog_table.getModel();
            while(model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
            String name = searchName_textbox.getText();
            try{
                String sql = ("SELECT * FROM `bookings` WHERE `Booked_by_lastname` ='"+name+"'");
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {

                   
                    String firstname = rs.getString("Booked_by_firstname");
                    String lastname = rs.getString("Booked_by_lastname");
                    String bookedFrom = rs.getString("Booked_from");
                    String bookedTo = rs.getString("Booked_to");
                    String bookedRooms = rs.getString("Booked_rooms");
                    String totcost = rs.getString("Total_cost");
                    String num_children = rs.getString("No. of Children");
                    String num_adult = rs.getString("No. of Adults");
                    String bookingInd = String.valueOf(rs.getInt("Ind_book"));
                    model.addRow(new Object [] {bookingInd ,firstname,lastname,bookedFrom,bookedTo,bookedRooms,totcost,num_children,num_adult});
                    datechooser_Search.setDate(null);

                }
            }
            catch(SQLException e){ System.out.println(e);}
        }

        // get records by Date
        if (Searchby.equals("Date"))
        {
            DefaultTableModel model = (DefaultTableModel)bookingLog_table.getModel();
            while(model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String date = sdf.format(datechooser_Search.getDate());
            try{
                String sql = ("SELECT * FROM `bookings` WHERE `Booked_from` <='"+date+"'  AND '"+date+"'<=`Booked_to`");
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {

                   
                    String firstname = rs.getString("Booked_by_firstname");
                    String lastname = rs.getString("Booked_by_lastname");
                    String bookedFrom = rs.getString("Booked_from");
                    String bookedTo = rs.getString("Booked_to");
                    String bookedRooms = rs.getString("Booked_rooms");
                    String totcost = rs.getString("Total_cost");
                    String num_children = rs.getString("No. of Children");
                    String num_adult = rs.getString("No. of Adults");
                    String bookingInd = String.valueOf(rs.getInt("Ind_book"));
                    model.addRow(new Object [] {bookingInd ,firstname,lastname,bookedFrom,bookedTo,bookedRooms,totcost,num_children,num_adult});
                    searchName_textbox.setText(null);

                }
            }
            catch(SQLException e){ System.out.println(e);}
        }
    }//GEN-LAST:event_search_buttonActionPerformed

    private void editSelectedBooking_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSelectedBooking_buttonActionPerformed
        // TODO add your handling code here:
         DefaultTableModel model = (DefaultTableModel)bookingLog_table.getModel();
        int select = bookingLog_table.getSelectedRow();
        selectedBookingIndex =model.getValueAt(select,0).toString();
        
        selectedRoom_textBox.setText("");
        
         
            while(model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
        
            new Booking_edit(selectedBookingIndex, cn).setVisible(true);
            
    }//GEN-LAST:event_editSelectedBooking_buttonActionPerformed

    private void selectedRoom_textBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedRoom_textBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_selectedRoom_textBoxMouseClicked

    private void selectedRoom_textBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectedRoom_textBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectedRoom_textBoxActionPerformed

    private void for_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_for_allActionPerformed
        // TODO add your handling code here:
        Searchby = "";
    }//GEN-LAST:event_for_allActionPerformed

    private void master_paneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_master_paneMouseClicked
        // TODO add your handling code here:
      
       
        
    }//GEN-LAST:event_master_paneMouseClicked

    private void master_paneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_master_paneMousePressed
        // TODO add your handling code here:
        
       
        
        
    }//GEN-LAST:event_master_paneMousePressed
   
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
     
    }//GEN-LAST:event_jLabel1MouseClicked
static int xx,yy;
    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        
       xx = evt.getX();
       yy = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
        // TODO add your handling code here:
      
         
    }//GEN-LAST:event_jLabel1MouseReleased

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx,y-yy);
        
    }//GEN-LAST:event_jLabel1MouseDragged

    private void closeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButton1ActionPerformed
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_closeButton1ActionPerformed

    private void closeButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButton2ActionPerformed
        // TODO add your handling code here:
        
        
        
       // xtxfhxcrxytjcjrxjxcty
        if(size)
        {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            int x =this.getHeight();
            int e = this.getWidth();
            System.out.println(e+" "+x);
            size = false;
        }
        else
        {
            this.setSize(997, 678);
            this.setLocationRelativeTo(null);
            size = true;
        }
        
        
        
        
    }//GEN-LAST:event_closeButton2ActionPerformed

    private void firstName_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstName_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_firstName_textboxMouseClicked

    private void firstName_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstName_textboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstName_textboxActionPerformed

    private void lastName_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lastName_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lastName_textboxMouseClicked

    private void noFoAdults_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noFoAdults_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_noFoAdults_textboxMouseClicked

    private void noFoAdults_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noFoAdults_textboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noFoAdults_textboxActionPerformed

    private void noFoChildren_textboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noFoChildren_textboxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_noFoChildren_textboxMouseClicked

    private void bookConfirm_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookConfirm_buttonActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //get all the information
        String firstname = firstName_textbox.getText();
        String lastname = lastName_textbox.getText();
        String datefrom = "";
        String dateto = "";
        try{
            datefrom = sdf.format(dateFrom_datechooser.getDate());
            dateto = sdf.format(dateTo_datechooser.getDate());}
        catch(NullPointerException e){System.out.println(e);}
        String num_adult = noFoAdults_textbox.getText();
        String num_children = noFoChildren_textbox.getText();

        // check for missing information
        String c = "";
        long millis=System.currentTimeMillis();
        java.sql.Date today =new java.sql.Date(millis);
        String Today = sdf.format(today);

        boolean c1 = firstname.equals(c);
        boolean c2 = lastname.equals(c);
        boolean c3 = Today.compareTo(datefrom) > 0;
        boolean c4 = Today.compareTo(dateto) > 0;
        boolean c34 = datefrom.compareTo(dateto)>0;
        boolean c33 = dateto.equals("");
        boolean c44 = datefrom.equals("");
        boolean c5 = num_adult.equals(c);
        boolean c6 = num_children.equals(c);
        boolean c7 = image_indentification ==null;
        boolean ct = false;
        if (c1==false && c2==false && c3==false && c4==false && c34==false && c5==false && c6==false && c7 == false ){ct = true;}
        // show according to the checks
        if (c1){error1_lable_book.setVisible(true);}
        if (c2){error2_lable_book.setVisible(true);}
        if (c3){error5_lable_book.setVisible(true);}
        if (c33){error5_lable_book.setVisible(true);}
        if (c4){error5_lable_book.setVisible(true);}
        if (c44){error5_lable_book.setVisible(true);}
        if (c34){error5_lable_book.setVisible(true);}
        if (c5){error3_lable_book.setVisible(true);}
        if (c6){error4_lable_book.setVisible(true);}
        if(c7){error565_lable_book.setVisible(true);}
        System.out.println(ct);
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c4);
        System.out.println(c5);
        System.out.println(c6);
        System.out.println(c34);
        //send info to booking confirmation window
        if (ct)
        {new Booking_choose_room(firstname,lastname,num_children,num_adult,datefrom,dateto,_username, image_indentification, cn ).setVisible(true);
            this.dispose();}
    }//GEN-LAST:event_bookConfirm_buttonActionPerformed

    private void dateFrom_datechooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateFrom_datechooserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFrom_datechooserMouseClicked

    private void dateFrom_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateFrom_labelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFrom_labelMouseClicked

    private void dateTo_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateTo_labelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTo_labelMouseClicked

    private void dateTo_datechooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateTo_datechooserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTo_datechooserMouseClicked

    private void addIdentification_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIdentification_buttonActionPerformed
        // add image of identification that has been scanned
        error565_lable_book.setVisible(false);
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        filename = f.getAbsolutePath();

        try
        {
            File image = new File(filename);
            FileInputStream fis =   new FileInputStream(image);
            ImageIcon imageicon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(icon.getWidth(),icon.getHeight(), Image.SCALE_SMOOTH));
            System.out.print(imageicon);
            icon.setIcon(imageicon);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte [] buf =  new byte[1024];
            for(int readNum; (readNum = fis.read(buf))!= -1;)
            {
                bos.write(buf,0,readNum);
            }
            image_indentification = bos.toByteArray();
        }
        catch(IOException e )
        {
            JOptionPane.showMessageDialog(null, e +"");

        }

        addIdentification_button.setText(filename);
        System.out.println(Arrays.toString(image_indentification));
    }//GEN-LAST:event_addIdentification_buttonActionPerformed

    private void date_chooser_avaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_date_chooser_avaMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_date_chooser_avaMouseClicked

    private void totalRoomsAvailable_textboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalRoomsAvailable_textboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalRoomsAvailable_textboxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> RoomType_combobox;
    private javax.swing.ButtonGroup SearchBy_bookinglog;
    private javax.swing.JButton addIdentification_button;
    private javax.swing.JButton adminOptions_button;
    private javax.swing.JButton adminOptions_button1;
    private javax.swing.JPanel availability_pane;
    private javax.swing.JButton avaliablity_button;
    private javax.swing.JButton bookConfirm_button;
    private javax.swing.JPanel bookingLog_pane;
    private javax.swing.JTable bookingLog_table;
    private javax.swing.JButton booking_button;
    private javax.swing.JPanel booking_pane;
    private javax.swing.JButton check_button;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton closeButton1;
    private javax.swing.JButton closeButton2;
    private com.toedter.calendar.JDateChooser dateFrom_datechooser;
    private javax.swing.JLabel dateFrom_label;
    private com.toedter.calendar.JDateChooser dateTo_datechooser;
    private javax.swing.JLabel dateTo_label;
    private com.toedter.calendar.JDateChooser date_chooser_ava;
    private com.toedter.calendar.JDateChooser datechooser_Search;
    private javax.swing.JButton deleteSelectedBooking_button;
    private javax.swing.JButton editSelectedBooking_button;
    private javax.swing.JLabel error1_lable;
    private javax.swing.JLabel error1_lable_book;
    private javax.swing.JLabel error2_lable;
    private javax.swing.JLabel error2_lable_book;
    private javax.swing.JLabel error3_lable_book;
    private javax.swing.JLabel error4_lable_book;
    private javax.swing.JLabel error565_lable_book;
    private javax.swing.JLabel error5_lable_book;
    private javax.swing.JLabel firstName_lable;
    private javax.swing.JLabel firstName_lable1;
    private javax.swing.JLabel firstName_lable2;
    private javax.swing.JLabel firstName_lable3;
    private javax.swing.JTextField firstName_textbox;
    private javax.swing.JRadioButton for_all;
    private javax.swing.JLabel hotel_lable;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel identificationNo_lable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lastName_lable;
    private javax.swing.JTextField lastName_textbox;
    private javax.swing.JButton logOut_Button;
    private javax.swing.JLayeredPane masterLayered_pane;
    private javax.swing.JPanel master_pane;
    private javax.swing.JLabel noFoAdults_label;
    private javax.swing.JTextField noFoAdults_textbox;
    private javax.swing.JLabel noFoChildren_label;
    private javax.swing.JTextField noFoChildren_textbox;
    private javax.swing.JLabel reservation_lable;
    private javax.swing.JTextField searchName_textbox;
    private javax.swing.JButton search_button;
    private javax.swing.JRadioButton search_date;
    private javax.swing.JRadioButton search_firstname;
    private javax.swing.JRadioButton search_lastname;
    private javax.swing.JTextField selectedRoom_textBox;
    private javax.swing.JLabel style1_lable;
    private javax.swing.JPanel style1_pane;
    private javax.swing.JLabel style2_lable;
    private javax.swing.JLabel totalRoomsAvailableOf_lable;
    private javax.swing.JTextField totalRoomsAvailableOf_textbox;
    private javax.swing.JLabel totalRoomsAvailable_lable;
    private javax.swing.JLabel totalRoomsAvailable_lable1;
    private javax.swing.JTextField totalRoomsAvailable_textbox;
    private javax.swing.JTextField totalRoomsOf_textbox;
    private javax.swing.JTextField totalRooms_textbox;
    // End of variables declaration//GEN-END:variables

   
    

}
