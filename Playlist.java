/*  This class represents a Playlist of podcast episodes, where each
/*  episode is implemented as an object of type Episode. A user navigating
/*  a Playlist should be able to move between songs using Next or Previous.
/*
/*  To enable flexible navigation, the Playlist is implemented as
/*  a Circular Doubly Linked List where each episode has a link to both
/*  the next and the prev episodes in the list.
/*
/*  Additionally, the last Episode is linked to the head of the list (via next),
/*  and the head of the list is linked to that last Episode (via prev). That said,
/*  there is NO special "last" reference keeping track of the last Episode.
/*  But there is a "head" reference that should always refer to the first Episode.
*/

/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
Jacob Choi*/

public class Playlist
{
   private Episode head;
   private int size;

   public Playlist(){
      head = null;
      size = 0;
   }

   public boolean isEmpty()
   { return head == null; }

   // Ensure that "size" is updated properly in other methods, to
   // always reflect the correct number of episodes in the current playlist
   public int getSize()
   { return this.size; }

   // Displays the Episodes starting from the head and moving forward
   // Example code and its expected output:
   /*   Playlist pl = new Playlist();
   /*   pl.addLast("PlanetMoney",26.0);
   /*   pl.addLast("TodayExplained",10);
   /*   pl.addLast("RadioLab",25.5);
   /*   pl.displayPlaylistForward();
   /* [BEGIN] (PlanetMoney|26.0MIN) -> (TodayExplained|10.0MIN) -> (RadioLab|25.5MIN) [END]
   */
   public void displayPlaylistForward()
   {
     String output = "[BEGIN] ";
     Episode current = head;
     if( current != null ){
       while( current.next != head ){
         output += current + " -> ";
         current = current.next;
       }
       output += current + " [END]\n";
     }
     else{
       output += " [END]\n";
     }
     System.out.println(output);
   }

   // Displays the Episodes starting from the end and moving backwards
   // Example code and its expected output:
   /*   Playlist pl = new Playlist();
   /*   pl.addLast("PlanetMoney",26.0);
   /*   pl.addLast("HowIBuiltThis",10);
   /*   pl.addLast("RadioLab",25.5);
   /*   pl.displayPlaylistForward();
   /* [END] (RadioLab|25.5MIN) -> (HowIBuiltThis|10.0MIN) -> (PlanetMoney|26.0MIN) [BEGIN]
   */
   public void displayPlaylistBackward()
   {
    String output = "[END] ";
    if(!isEmpty()){
      Episode current = head.prev;
      while(current.prev != head.prev){
        output += current + " -> ";
        current = current.prev;
      }
    output += current + " [BEGIN]\n";
    }
      else{
        output += " [BEGIN]\n";
      }
      System.out.println(output);
   }

   // Add a new Episode at the beginning of the Playlist
   public void addFirst( String title, double length )
   {
      //if playlist is empty, a new episode will be added
      if(isEmpty()){
      Episode temp = new Episode(title, length, null, null);
      temp.next = temp;
      temp.prev = temp;
      head = temp;
      } 
      else{
      Episode temp = new Episode(title, length, head, head.prev);
      head = temp;
      head.prev.next = head;
      head.next.prev = head;
      }
      size++;
   }

   // Add a new Episode at the end of the Playlist
   public void addLast( String title, double length )
   {
     //if playlist is empty, a new episode will be added
    if(isEmpty()){
      Episode temp = new Episode(title, length, null, null);
      temp.next = temp;
      temp.prev = temp;
      head = temp;
     } 
     else{
      Episode temp = new Episode(title, length, head, head.prev);
      head.prev.next = temp;
      head.prev = temp;
     }
     size++;
   }
   // Add a new Episode at the given index, assuming that index
   // zero corresponds to the first node
   public void add( String title, double length, int index )
   {
      //checks corner case if indicies are out of bounds 
      if(index < 0 || index > size) throw new IndexOutOfBoundsException();
      //checks corner case if index is at beginning/end of list
      else if(index == 0)addFirst(title, length);
      else if (index == size)addLast(title, length);
      else{
        Episode tmp = head;
        for(int i=0; i<index; i++){
          tmp = tmp.next;
        }
        //newly created episode
        Episode temp = new Episode(title, length, tmp, tmp.prev);
        tmp.prev.next = temp;
        tmp.prev = temp;
        size++;
      }
   }

   //Delete the first Episode in the Playlist
   public Episode deleteFirst()
   {
     //checks if playlist is empty or if only one episode is in the playlist
     if(isEmpty())throw new RuntimeException("[ERROR] Cannot delete from empty playlist!");
     else if(size == 1){
       Episode temp = head;
       head = null;
       size--;
       temp.next = null;
       temp.prev = null;
       return temp;
     }
     else {
      Episode temp = head;
      head = temp.next;
      head.prev = temp.prev;
      temp.prev.next = head;
      size--;
      temp.next = null;
      temp.prev = null;
      return temp;
     }
   }

   // Delete the last Episode in the Playlist
   // (There is no special "last" variable in this Playlist;
   // think of alternative ways to find that last Episode)
   public Episode deleteLast()
   {
     //checks if playlist is empty or if there's only one episode in the playlist
    if(isEmpty())throw new RuntimeException("[ERROR] Cannot delete from empty playlist!");
    else if(size == 1){
      Episode temp = head;
      head = null;
      size--;
      temp.next = null;
      temp.prev = null;
      return temp;
    }
    else{
      Episode temp = head.prev;
      temp.prev.next = head;
      head.prev = temp.prev;
      size--;

      temp.next = null;
      temp.prev = null;
      return temp;
    }
   }
   // Remove (delete) the Episode that has the given "title"
   // You can assume there will be no duplicate titles in any Playlist
   public Episode deleteEpisode(String title)
   {
     //checks if playlist is empty or has one episode
    if(isEmpty()) throw new RuntimeException("[ERROR] Cannot delete from empty playlist!");
    else if(size == 1) {
      Episode temp = head;
      head = null;
      size--;
      return temp;
    }
    //checks if title is in head
    else if(head.getTitle().equals(title)) {
      return deleteFirst();
    }
    //checks if title is at end of playlist
    else if(head.prev.getTitle().equals(title)){
      return deleteLast();
    }
    //if title is in the middle
    else{
      Episode curr = head.next;
      Episode prev = head;
      while(curr != head && !curr.getTitle().equals(title)){
        prev = curr;
        curr = curr.next;
      } 
      //throws exception when title is not in the playlist
      if(curr == head){
        throw new RuntimeException("[ERROR] Title not found in playlist!");
      }
      prev.next = curr.next;
      curr.next.prev = prev;

      size--;
      curr.next = null;
      curr.prev = null;
      return curr;
    }
   }

   // Remove (delete) every m-th Episode in the user's circular Playlist,
   // until only one Episode survives. Return the survived Episode.
   public Episode deleteEveryMthEpisode(int m)
   {
    //checks if playlist is empty or if m value is within range
    if(isEmpty())throw new RuntimeException("[ERROR] Cannot delete from empty playlist!");
    if(m < 1) throw new RuntimeException("[ERROR] Invalid m value!");
    Episode temp = head.prev;
    while(getSize()!=1){

      for(int i=0;i<m;i++){
        temp = temp.next;
      }
      //corner case if temp is at head
      if(temp == head){
        head = temp.next;
        head.prev = temp.prev;
        temp.prev.next = head;
      }
      //corner case if temp is at end of list
      else if(temp == head.prev){
        temp.prev.next = head;
        head.prev = temp.prev;
      }
      else{
        temp.next.prev = temp.prev;
        temp.prev.next = temp.next;
      }
      size--;
    }
    return temp;
   }
} // End of Playlist class
