/*
█▀▄  ▀  ▄▀▀  ▄▀▀  █    ▄▀▄  ▀  █▄ ▄█  █▀▀  █▀▀▄
█ █  █   ▀▄  █    █    █▀█  █  █ █ █  █▀▀  █▐█▀
▀▀   ▀  ▀▀    ▀▀  ▀▀▀  ▀ ▀  ▀  ▀   ▀  ▀▀▀  ▀ ▀▀
LIMITATIONS:
-xml must not contain nested nodes in one line (NOT like "<node1><node2>content</node2></node1>")
-xml text nodes must be one liners (like "<myTextNode>content</myTextNode>")
-xml lines where comments can be found, must not contain any nodes (NOT like "<!--myComment--><myNode>content</myNode>")
Created by Levente Nagy
Version 1.0
2019.12.08
 */

package xmlHandler;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**This class is used to process xml files and generate xml nodes out of them.*/
public class xmlReader
{
  ArrayList<String> Text;
  node rootNode;

  /**The constructor reads the xml file specified in the argument and processes the root node.*/
  public xmlReader(String location)
  {
    try
    {
      FileReader reader = new FileReader(location);
      int data = reader.read();
      int charNo = 2;
      while (data != -1)
      {
        charNo++;
        data = reader.read();
      }
      reader = new FileReader(location);
      char[] chars = new char[charNo];
      reader.read(chars);
      String line;
      Text = new ArrayList<String>();
      int i, j;
      for (i = 0; i < chars.length - 1; i++)
      {
        line = "";
        for (j = i; chars[j] != '\n' && j < chars.length - 1; j++)
        {
          line += chars[j];
        }
        i = j;
        Text.add(line);
      }
      reader.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    int i, j;
    String rootTag;
    for(i = 0; i < Text.size(); i++)
    {
      if (Text.get(i).contains("<") && Text.get(i).contains(">") && !Text.get(i).contains("</") && !Text.get(i).contains("<?") && !Text.get(i).contains("<!"))
      {
        rootTag = Text.get(i).substring(Text.get(i).indexOf("<") + 1,Text.get(i).indexOf(">"));
        for (j = i + 1; j < Text.size(); j++)
        {
          if (Text.get(j).contains("</" + rootTag + ">"))
          {
            this.rootNode = new node(rootTag, new ArrayList<String>(Text.subList(i + 1, j)));
            break;
          }
        }
        break;
      }
    }
  }

  /**This getter returns the processed root node of the xml file.*/
  public node getRootNode()
  {
    return rootNode;
  }
}
