package loop;
import java.sql.*;
import java.text.SimpleDateFormat;


public class get_set_data {
public  Connection cn =null;
    public  Statement st = null;  
    public  void main(Connection con) {
        //conection();
        cn = con;
        checkBooking();
         
    }
    
    // checks booking for updating the occupancy of the rooms and other attributes
    private void checkBooking()
    {
        try
        {
            String sql = "SELECT * FROM `bookings`";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                String booking_stat = rs.getString("stat");
                if(booking_stat.equals("yet"))
                {
                    // get the booking dates
                    String booked_date_from = rs.getDate("Booked_from").toString();
                    String booked_date_to = rs.getDate("Booked_to").toString();
                   
                    // get todays date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    long millis=System.currentTimeMillis();
                    java.sql.Date today =new java.sql.Date(millis);
                    String Today = sdf.format(today);
                    
                    //get the booking info
                    String fn = rs.getString("Booked_by_firstname");
                    String ln = rs.getString("Booked_by_lastname");
                    
                    
                    //comparison of dates
                    boolean c1 = booked_date_from.compareTo(Today) <= 0;
                    boolean c2 = booked_date_to.compareTo(Today) >= 0;
                    
                    System.out.println(c1+"  "+c2);
                    
                    //today is within the booked span, so set the room occupied
                    if(c1 == true && c2 == true)
                    {
                        break_rooms(rs.getString("Booked_rooms"),true, rs.getString("Ind_book"),fn,ln,booked_date_from,booked_date_to);
                    }
                    //today is after the total booked span, so set the booking to been and rooms to vacant 
                    if(c1 == true && c2 == false)
                    {
                        break_rooms(rs.getString("Booked_rooms"),false, rs.getString("Ind_book"),fn,ln,booked_date_from,booked_date_to);
                    }
                    
                }    
            }
        }
        catch(SQLException e )
        {
            System.out.print("1");
        }
    }
    private void break_rooms(String rooms,boolean check, String booking_ind, String fn, String ln,String bf,String bt)
    {
        String rm = rooms;
                int num_words = 0;
                int y = rm.length();
                // to get the tot number of rooms in booking
                for(int z=0; z<y ; z++)
                {
                    String t =rm.substring(z,z+1);
                    if(t.equals(","))
                    {
                        num_words++;
                    }
                }
                
                
                // array to store each room
                String[] list = new String[num_words];
                String t2 ="";
                int ct = 0;
                //breaks all the roooms and puts them into the array
                for(int z=0; z<y ; z++)
                {
                    String t = rm.substring(z,z+1);
                    
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
                
                //set occupy or vacant depending on the date comparision in previous method    
                    if(check)
                    {
                        set_occupy(list,num_words,booking_ind,fn,ln,bf,bt);
                    }
                    if(check == false)
                    {
                        System.out.println("here");
                        set_vacant(list,num_words);
                    }
                
                //set stat of a booking been cause today has passed the span of booked dates
                if(check == false)
                {
                    set_booking_been(booking_ind);
                }
    }
    
    private void set_occupy(String[] room,int num, String booking_ind, String first_name, String last_name, String bookfrom, String bookto)
    {
        System.out.println(room[0]);
        if(num>0){
        for(int s=0 ; s<num; s++){
        try
        {
            String sql ="UPDATE `each_room` SET `room_occupancy`='Occupied',`booked_from`= '"+bookfrom+"',`booked_to`='"+bookto+"',"
                    + "`Booked_by_firstname`='"+first_name
                    +"',`Booked_by_lastname`='"+last_name+"',`Related_bookingIndex`='"+booking_ind+"' WHERE `room_no` ='"+room[s]+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            int rs = ps.executeUpdate();
        }
        catch(SQLException e)
        {
           System.out.print("2" );
        }}}
    }
    private void set_vacant(String []room, int num)
    {
        System.out.println(num);
        if(num>0){
        for(int s=0 ; s<num; s++){    
        try
        {
            String sql ="UPDATE `each_room` SET `room_occupancy`='Vacant',`booked_from`= null,`booked_to`=null,`Booked_by_firstname`='',"
                    + "`Booked_by_lastname`='',`Related_bookingIndex`='' WHERE `room_no` = '"
                    +room[s]+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            int rs = ps.executeUpdate();
            
        }
        catch(SQLException e )
        {
           System.out.print("3");
        }}}
    }
    private void set_booking_been(String booking_ind)
    {
         try
        {
            String sql ="UPDATE `bookings` SET `stat`='been' WHERE `Ind_book` = '"+booking_ind+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            int rs = ps.executeUpdate();
        }
        catch(SQLException e )
        {
           System.out.print("4");
        }
    }
}
