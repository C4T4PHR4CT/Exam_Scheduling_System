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

import java.util.ArrayList;

/**This class describes a xml node, with the properties of its:</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Name, tag name</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Content, every text including nodes in text form inside this node</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• isTextNode, describes certain behaviour of this node<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Children, every direct child of this node<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Level, the number of levels above this node until the root node*/
public class node
{
  /**Every text including nodes in text form inside this node*/
  private ArrayList<String> Content;
  /**Tag name of this node*/
  String Name;
  /**Type of this node*/
  boolean isTextNode;
  /**The text of this node, if it is a text node*/
  String Text;
  /**Every direct child of this node*/
  ArrayList<node> Children;
  /**The number of levels above this node until the root node, which has a predefined integer level, and this' level is relative to the root's one*/
  int Level;

  /**This constructor creates a xml node with its tag name and raw content, and after it it recursively creates its children based on the raw content.*/
  public node(String Name, ArrayList<String> Content)
  {
    this.Name = Name;
    this.Content = Content;
    this.isTextNode = false;
    Children = new ArrayList<node>();

    String tag;
    for (int i = 0; i < Content.size(); i++)
    {
      tag = "";
      if (Content.get(i).contains("<") && Content.get(i).contains(">") && !Content.get(i).contains("</") && !Content.get(i).contains("<!"))
      {
        tag = Content.get(i).substring(Content.get(i).indexOf("<") + 1,
            Content.get(i).indexOf(">"));
        for (int j = i + 1; j < Content.size(); j++)
        {
          if (Content.get(j).contains("</" + tag + ">"))
          {
            Children.add(new node(tag,
                new ArrayList<String>(Content.subList(i + 1, j))));
            i = j;
            break;
          }
        }
      }
      if (Content.get(i).split("<").length == 3 && Content.get(i).contains("</"))
      {
        tag = Content.get(i).substring(Content.get(i).indexOf("<") + 1,
            Content.get(i).indexOf(">"));
        Children.add(new node(tag, Content.get(i)
            .substring(Content.get(i).indexOf(">") + 1, Content.get(i).indexOf("</"))));
      }
    }
  }

  /**This constructor creates a text node with its tag name and its text value.*/
  public node(String Name, String Text)
  {
    this.Name = Name;
    this.Text = Text;
    this.isTextNode = true;
  }

  /**This getter returns the tag name of this node.*/
  public String getName()
  {
    return Name;
  }

  /**This getter returns the text value of this node, if its a text node, or else it throws an exception.*/
  public String getText()
  {
    if (!isTextNode)
      throw new IllegalStateException("Unable to get text of a non text node!");
    return Text;
  }

  /**This getter returns all of the children of this node, if its not a text node, or else it throws an exception.*/
  public node[] getChildren()
  {
    if (isTextNode)
      throw new IllegalStateException(
          "Unable to get children nodes of a text node!");
    node[] temp = new node[Children.size()];
    temp = Children.toArray(temp);
    return temp;
  }

  /**This getter returns all of the children of this node with the specified tag name in the argument, if its not a text node, or else it throws an exception.*/
  public node[] getChildrenWithName(String Name)
  {
    if (isTextNode)
      throw new IllegalStateException(
          "Unable to get children nodes of a text node!");
    ArrayList<node> childrenWname = new ArrayList<node>();
    for (int i = 0; i < Children.size(); i++)
      if (Children.get(i).getName().equals(Name))
        childrenWname.add(Children.get(i));
    node[] temp = new node[childrenWname.size()];
    temp = childrenWname.toArray(temp);
    return temp;
  }

  /**This constructor creates a xml node with its tag name and its level.*/
  public node(String Name, int Level)
  {
    Children = new ArrayList<node>();
    this.Name = Name;
    this.isTextNode = false;
    this.Level = Level;
  }

  /**This constructor creates a text node with its tag name, text value and its level.*/
  public node(String Name, String Text, int Level)
  {
    this.Name = Name;
    this.Text = Text;
    this.isTextNode = true;
    this.Level = Level;
  }

  /**This constructor creates a text node with its tag name, text value and its level.*/
  public void addChild(String Name, String Text)
  {
    if (isTextNode)
      throw new IllegalStateException("Unable to add child node to a text node!");
    Children.add(new node(Name, Text, Level + 1));
  }

  /**This method adds a child node to this node with its tag name*/
  public void addChild(String Name)
  {
    if (isTextNode)
      throw new IllegalStateException("Unable to add child node to a text node!");
    Children.add(new node(Name, Level + 1));
  }

  /**This method overrides the default toString method by returning the properties of this node*/
  public String toString()
  {
    String line = "";
    line += Name + "\n";
    if (isTextNode)
      line += "TextNode\n";
    else
      line += "TreeNode\n";
    for (int i = 0; i < Content.size(); i++)
    {
      line += Content.get(i) + "\n";
    }
    return line;
  }
}
