/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Frank
 */
public class Booking_room_change extends javax.swing.JFrame {

    //public variables
     public Connection cn;
    public Statement st;
     private boolean search_bol;
    public String bookingind;
    public byte[] image_indentification;
    
    
    public String firstname;
    public String lastname;
    public String Datefrom;
    public String Dateto;
    public String Num_adult;
    public String Num_children;
    public String Bookindex;
    public int cost_each_room;
    public String Booked_rooms;
    private String room_list = "";
    private String c_booking_rm;
    private boolean chop = false;
    
    

        
    // get the bookings in the span of this current booking 
    private void get_bookings()
    {
        try
        {
            String sql = "SELECT * FROM `bookings` ";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                boolean cc1 = Datefrom == null;
                boolean cc2 = Dateto == null;
                
                 if (cc1 == false && cc2 == false)
                 {
               
                // get & set all the variables 
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String bookedrm = rs.getString("Booked_rooms");
                String datef = sdf.format(rs.getDate("Booked_from"));
                String datet = sdf.format(rs.getDate("Booked_to"));
                
                //set check booleans 
                boolean c1 = Datefrom.compareTo(datef)>=0;
                boolean c2 = Dateto.compareTo(datef)>=0;
                boolean c3 = Datefrom.compareTo(datet)>=0;
                boolean c4 = Dateto.compareTo(datet)>=0;
                
                
                // compare the checks
                int t = 0;
                if(c1 == false && c2 == true && c3 == false && c4 == true){t++;}
                if(c1 == true && c2 == true && c3 == false && c4 == false){t++;}
                if(c1 == false && c2 == true && c3 == false && c4 == false){t++;}
                if(c1 == true && c2 == true && c3 == false && c4 == true){t++;}
                if(t>0){room_list = room_list + bookedrm;}
                 }
            }
        }
        catch(SQLException e )
        {
            System.out.println("booking _room_change  line-111");
            System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());
        }
    }
    
    //get the current booking rooms
    private void get_c_bookingRoom(String BIX)
    {
        
        try 
        {
            String sql = "SELECT * FROM `bookings` WHERE `Ind_book`= '"+BIX+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                c_booking_rm = rs.getString("Booked_rooms") ;
                room_list = room_list + c_booking_rm;
                Break_cb_rn(rs.getString("Booked_rooms"));
            }
            
        }
        catch(SQLException e)
        {
            System.out.println("booking _room_change  line-135");
            System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());
        }
    }
    
    //break the room numbers
    private void Break_cb_rn(String rns )
    {
         int num_words = 0;
        // array to store each room
        
                String t2 ="";
                if(rns.equals("")!= true)
                {
                int y = rns.length();
                // to get the tot number of rooms in booking
                for(int z=0; z<y ; z++)
                {
                    String t =rns.substring(z,z+1);
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
                    String t = rns.substring(z,z+1);
                    
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
                    get_rT(list,num_words );   
                }
                
    }
    
    //get room type of each room
    private void get_rT(String[] rn, int numCT  )
    {
        try
        {
            for(int w = 0 ;w<numCT; w++)
            {
            String sql = "SELECT * FROM `each_room` WHERE `room_no` = '"+rn[w]+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                get_Rc(rn[w],rs.getString("room_type"));
            }}
        }
        catch(SQLException e)
        {
            System.out.println("booking _room_change  line-200");
            System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());
        }
    }
    
    // get cost of each night of each room 
    private void get_Rc(String rn, String RT)
    {
        try
        {
            String sql = "SELECT * FROM `rooms` WHERE `Room_type` ='"+RT+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                set_table(rn,RT, rs.getString("cost_per_night"));
            }
        }
        catch(SQLException e)
        {
            System.out.println("booking _room_change  line-219");
            System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());
        }
    }
    //Set table 
    private void set_table(String rn, String RT, String rc)
    {
        DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
        model.addRow(new Object[] {rn,RT,rc,true});
        
    }
    
      // get room type cost 
    private void get_RT_cost(String RT)
    {
        DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
        String roomtype = roomType_combobox.getSelectedItem().toString();
        
        try
        {
            String sql ="SELECT * FROM `rooms`WHERE `Room_type` = '"+RT+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                cost_each_room = rs.getInt("cost_per_night");
                array_of_bookedrooms( room_list,rs.getInt("cost_per_night"),RT);
            }
        }
        catch(SQLException e )
        {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());
        }
    }
    
    
    //get all the booked rooms and puts them in an array
    private void array_of_bookedrooms(String RM,int RT_cost ,String RT)
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
                set_table(RT, RT_cost, list, num_words);
                    
                }
                else
                {
                    String[] list =new String[1];
                    list[0]="";
                    set_table(RT, RT_cost, list, num_words);
                }
                
                
                
    }
    
  private void set_table(String RT, int RT_cost, String[] rnl, int num )
  {
      
                
                 DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
                 int row_ct = model.getRowCount();
                    try 
                    {
                        String sql = "SELECT * FROM `each_room` WHERE `room_in_out_service` ='In Service' And  `room_type` ='"+RT+"'";
                        PreparedStatement ps = cn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        
                        while(rs.next())
                        {
                            String rn = rs.getString("room_no");
                            if(num >0)
                            {
                                int ch = 0;
                                for(int x = 0 ; x<num ; x++)
                                 { String t = rnl[x];
                                     if(rn.equals(t)== true)
                                    {
                                        ch++;
                                    }    
                                 }
                                if(ch == 0)
                                {
                                    int ph = 0;
                                for(int x = 0 ; x < row_ct; x++ )
                                {
                                    String t = model.getValueAt(x, 1).toString();
                                    String oo = model.getValueAt(x, 3).toString();
                                    if(t.equals(RT) && oo.equals("false") )
                                    {
                                        ph = ph+1 ;
                                    }
                                }
                                if(ph == 0)
                                {
                                    model.addRow(new Object[] {rn,RT,RT_cost,false});
                                }
                                }
                            }
                            else
                            {
                                
                              int ch = 0;
                                for(int x = 0 ; x < row_ct; x++ )
                                {
                                    String t = model.getValueAt(x, 1).toString();
                                    if(t.equals(RT) )
                                    {
                                        ch = ch+1 ;
                                    }
                                }
                                if(ch == 0)
                                {
                                    model.addRow(new Object[] {rn,RT,RT_cost,false});
                                }

                            
                                
                            }
                        }
                    }
                    catch(SQLException e)
                    {
                        JOptionPane.showMessageDialog(null, e);
                        System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());
                    }
  }
    
    
   
    
    
      public  void Update_edit(String ftName , String ltName, String datefrom , String dateto, String num_adult, String num_children, String bookind, String All_Room_Nos, int  Total_cost, int count,String[] RoomNos)
    {
 
                
                
                 int child_no = Integer.parseInt(num_children);
        int adult_no = Integer.parseInt(num_adult);
        int book_ind= Integer.parseInt(bookind);
        
        
         try {
                //String sql = "UPDATE `bookings` SET `Booked_by_firstname`='"+ftName+"',`Booked_by_lastname`='"+ltName+"',`Booked_from`='"+datefrom+"',`Booked_to`='"+dateto+"',`No. of Children`='"+num_children+"',`No. of Adults`='"+num_adult+"',`identification`='"+image_indentification+"',`Booked_rooms` = '"+All_Room_Nos+"' WHERE  `Ind_book` ='"+bookind+"'";
                
                String sql = "UPDATE `bookings` SET `Booked_by_firstname`=?,`Booked_by_lastname`=?,`Booked_from`=?,`Booked_to`=?,`No. of Children`=?,`No. of Adults`=?,`identification`=?,`Booked_rooms` = ?"+
                        " WHERE  `Ind_book` =?";
                //PreparedStatement ps = cn.prepareStatement(sql);
                //int rs = ps.executeUpdate();
                
                 //prepare statement 
                /*String sql =("UPDATE `bookings` SET (`Booked_by_firstname`, `Booked_by_lastname`, `Booked_from`, `Booked_to`, `Booked_rooms`, "
                        + "`Total_cost`, `No. of Children`, `No. of Adults`,`Ind_book`,`identification`,`stat`) VALUES (?,?,?,?,?,?,?,?,?,?,?)") ;*/
                PreparedStatement ps = cn.prepareStatement(sql);


               //set values
                ps.setString(1,ftName );
                ps.setString(2,ltName );
                ps.setString(3,datefrom );
                ps.setString(4,dateto ); 
                ps.setInt(5,child_no );
                ps.setInt(6,adult_no );
                ps.setBytes(7, image_indentification);
                ps.setString(8,All_Room_Nos );
                ps.setInt(9, book_ind);
                // execute query
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "submitted");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());
                System.out.println(e);
                
            }
        
    }
    
    
      
     /*  public void reset_eachroom_booked( String bookind )
    {
        
         try {
                
                String sql = "UPDATE `each_room` SET `room_occupancy`='Vacant',`booked_from` =null,`booked_to`=null ,`Booked_by_firstname`='',`Booked_by_lastname`='', `Related_bookingIndex` ='' WHERE `Related_bookingIndex` = '"+bookind+"'";
                PreparedStatement ps = cn.prepareStatement(sql);
                int rs = ps.executeUpdate();
                update();
  
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());
            }
    }*/
    
    
     // updates app the data according to the data entered in all the tables 
    public void update()
    {
        get_set_data pop = new get_set_data();
        pop.main(cn);
        System.out.println("Done");
    }

    public void RoomType_combobox()
    {
         try{
            String sql = ("SELECT `Room_type` FROM `rooms`  ;");
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                String top = rs.getString("Room_type");
                System.out.println(top);
                roomType_combobox.addItem(top);
               
                
             
            }
        }
        catch(SQLException e){ System.out.println(e);System.out.println("I'm in line #" + 
    new Exception().getStackTrace()[0].getLineNumber());}
    }
    
    
    
    
    public Booking_room_change() {
        initComponents();
        this.setLocationRelativeTo(null);
        update();
        RoomType_combobox();
        search_bol = true;
        get_bookings();
        get_c_bookingRoom(Bookindex);
    }
    
    
    
    public Booking_room_change(String BookingIndex,String ftName , String ltName, String datefrom , String dateto, String num_adult, String num_children,byte[] image_indenti, Connection con) {
        initComponents();
        this.setLocationRelativeTo(null);
        cn = con;
        update();
        RoomType_combobox();
        bookingind = BookingIndex;
        
        image_indentification=image_indenti;
         firstname = ftName;
         lastname = ltName;
         Datefrom = datefrom;
         Dateto = dateto;
         Num_adult = num_adult;
         Num_children = num_children;
         Bookindex = BookingIndex;
         search_bol = true;
         get_bookings();
         get_c_bookingRoom(BookingIndex);
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Master_pane = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        totalCost_lable = new javax.swing.JLabel();
        roomType_combobox = new javax.swing.JComboBox<>();
        roomtype_lable = new javax.swing.JLabel();
        search_button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomChooser_table = new javax.swing.JTable();
        book_button = new javax.swing.JButton();
        reset_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        Master_pane.setBackground(new java.awt.Color(255, 255, 255));
        Master_pane.setForeground(new java.awt.Color(255, 255, 255));
        Master_pane.setPreferredSize(new java.awt.Dimension(700, 700));

        closeButton.setBackground(new java.awt.Color(0, 102, 255));
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setText("X");
        closeButton.setToolTipText("");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        totalCost_lable.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        totalCost_lable.setForeground(new java.awt.Color(51, 102, 255));
        totalCost_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalCost_lable.setText("Room Chooser");

        roomType_combobox.setBackground(new java.awt.Color(51, 102, 255));
        roomType_combobox.setForeground(new java.awt.Color(255, 255, 255));
        roomType_combobox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomType_comboboxMouseClicked(evt);
            }
        });
        roomType_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomType_comboboxActionPerformed(evt);
            }
        });

        roomtype_lable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        roomtype_lable.setForeground(new java.awt.Color(51, 102, 255));
        roomtype_lable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        roomtype_lable.setText("Room Type");

        search_button.setBackground(new java.awt.Color(0, 102, 255));
        search_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        search_button.setForeground(new java.awt.Color(255, 255, 255));
        search_button.setText("Search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });
        search_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_buttonKeyPressed(evt);
            }
        });

        roomChooser_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room No.", "Room Type", "Cost Per Night", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(roomChooser_table);

        book_button.setBackground(new java.awt.Color(0, 102, 255));
        book_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        book_button.setForeground(new java.awt.Color(255, 255, 255));
        book_button.setText("Book");
        book_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                book_buttonActionPerformed(evt);
            }
        });
        book_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                book_buttonKeyPressed(evt);
            }
        });

        reset_button.setBackground(new java.awt.Color(0, 102, 255));
        reset_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        reset_button.setForeground(new java.awt.Color(255, 255, 255));
        reset_button.setText("Reset");
        reset_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_buttonActionPerformed(evt);
            }
        });
        reset_button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                reset_buttonKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout Master_paneLayout = new javax.swing.GroupLayout(Master_pane);
        Master_pane.setLayout(Master_paneLayout);
        Master_paneLayout.setHorizontalGroup(
            Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Master_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Master_paneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(book_button, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(268, 268, 268))
                    .addGroup(Master_paneLayout.createSequentialGroup()
                        .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(roomtype_lable)
                            .addComponent(totalCost_lable))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Master_paneLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Master_paneLayout.createSequentialGroup()
                                .addComponent(roomType_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 108, Short.MAX_VALUE))))
                    .addGroup(Master_paneLayout.createSequentialGroup()
                        .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Master_paneLayout.createSequentialGroup()
                                .addComponent(reset_button, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        Master_paneLayout.setVerticalGroup(
            Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Master_paneLayout.createSequentialGroup()
                .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Master_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Master_paneLayout.createSequentialGroup()
                                .addComponent(totalCost_lable)
                                .addGap(18, 18, 18)
                                .addComponent(roomtype_lable))
                            .addGroup(Master_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(roomType_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reset_button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(book_button, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Master_pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Master_pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void roomType_comboboxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomType_comboboxMouseClicked

    }//GEN-LAST:event_roomType_comboboxMouseClicked

    private void roomType_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomType_comboboxActionPerformed

    }//GEN-LAST:event_roomType_comboboxActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed

        DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
        String roomtype = roomType_combobox.getSelectedItem().toString();
        int row_ct = model.getRowCount();
        
       if(chop)
       {
           get_c_bookingRoom(Bookindex);
       }
           
           
       System.out.println("ok1");
       get_RT_cost(roomtype);
      
       
       
    
       
    }//GEN-LAST:event_search_buttonActionPerformed

    private void search_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_buttonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_buttonKeyPressed

    private void book_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_book_buttonActionPerformed
        // TODO add your handling code here:
           
        DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
        String[] RoomNos = new String[model.getRowCount()];
        System.out.println(model.getRowCount());
         int count=0;
        while (model.getRowCount() > 0)
        {
            if (model.getValueAt(0, 3).toString().equals("true")){RoomNos[count]= model.getValueAt(0, 0).toString();count++; }
            model.removeRow(0);
        }
        int costEachRoom = cost_each_room ; 
        int Total_cost = costEachRoom*count; 
        
        
        ///
        int coun = count;
        String All="";
        while(coun>0)
        {
            coun--;
            All = All+RoomNos[coun]+",";
            System.out.println(RoomNos[coun]);
            
        }
        ///
        
        Update_edit(firstname, lastname,Datefrom,Dateto,Num_adult,Num_children,Bookindex,All,Total_cost, count ,RoomNos);
        
        this.dispose();


    }//GEN-LAST:event_book_buttonActionPerformed

    private void book_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_book_buttonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_book_buttonKeyPressed

    private void reset_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_buttonActionPerformed
       // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)roomChooser_table.getModel();
       
       while(model.getRowCount() > 0)
             {
              model.removeRow(0);
             }
        search_bol =true;
        chop = true;
    }//GEN-LAST:event_reset_buttonActionPerformed

    private void reset_buttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_reset_buttonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_reset_buttonKeyPressed

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
            java.util.logging.Logger.getLogger(Booking_room_change.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Booking_room_change.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Booking_room_change.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Booking_room_change.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Booking_room_change().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Master_pane;
    private javax.swing.JButton book_button;
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton reset_button;
    private javax.swing.JTable roomChooser_table;
    private javax.swing.JComboBox<String> roomType_combobox;
    private javax.swing.JLabel roomtype_lable;
    private javax.swing.JButton search_button;
    private javax.swing.JLabel totalCost_lable;
    // End of variables declaration//GEN-END:variables
}
