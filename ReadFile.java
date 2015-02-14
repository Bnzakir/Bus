import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadFile
{

  public static void main(String[] args) throws IOException
  {
    try
    {
      String filename = "data.dat"; 

      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);

      String data[] = new String[2];

      for (int i = 0; i < 2; i++)
        data[i] = br.readLine();

      br.close();    
       
      for (int i = 0; i < data.length; i++)
        System.out.println(data[i]);
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
