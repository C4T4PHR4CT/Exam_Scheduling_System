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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**This class is used to process xml nodes and write them to xml files.*/
public class xmlWriter
{
  /**The raw text of the xml*/
  ArrayList<String> Text;
  /**The number of line of the xml*/
  private int lnNum;

  /**The constructor initializes the Text ArrayList.*/
  public xmlWriter()
  {
    Text = new ArrayList<String>();
  }

  /**Creates or overwrites the xml file in the location specified in the argument, with the generated xml file from the root node inputted in the argument.*/
  public void write(String location, node rootNode)
  {
    Text.clear();
    Text.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    Text.add("");
    lnNum = 1;
    processNode(rootNode);
    for(int i = Text.size() - 1; i >= 0 && Text.get(i).equals(""); i--)
      Text.remove(i);

    try
    {
      FileWriter writer = new FileWriter(location);
      for (int i = 0; i < Text.size(); i++)
      {
        writer.write(Text.get(i));
        if (i + 1 != Text.size())
          writer.write("\n");
      }
      writer.flush();
      writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**Generates the xml to the Text ArrayList from the inputted node.*/
  private void processNode(node n)
  {
    if(n.isTextNode)
    {
      Text.add(lnNum,indent(n.Level) + "<" + n.Name + ">" + n.Text + "</" + n.Name + ">");
      lnNum++;
    }
    else
    {
      Text.add(lnNum,indent(n.Level) + "<" + n.Name + ">");
      lnNum++;
      Text.add(lnNum,indent(n.Level) + "</" + n.Name + ">");
      for(int i = 0; i < n.Children.size(); i++)
        processNode(n.Children.get(i));
      lnNum++;
    }
  }

  /**Returns a predefined number if spaces multiplied by the indent level in the argument*/
  private String indent(int level)
  {
    String temp = "";
    for (int i = 0; i < level; i++)
      temp += "    ";
    return temp;
  }
}
