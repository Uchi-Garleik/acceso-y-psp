package controller.action;

import javax.xml.parsers.ParserConfigurationException;

public interface IAction {
    public String execute(String[] request) throws ParserConfigurationException;
}
