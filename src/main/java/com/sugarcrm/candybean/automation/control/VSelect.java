/**
 * Candybean is a next generation automation and testing framework suite.
 * It is a collection of components that foster test automation, execution
 * configuration, data abstraction, results illustration, tag-based execution,
 * top-down and bottom-up batches, mobile variants, test translation across
 * languages, plain-language testing, and web service testing.
 * Copyright (C) 2013 SugarCRM, Inc. <candybean@sugarcrm.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sugarcrm.candybean.automation.control;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.sugarcrm.candybean.automation.VInterface;
import com.sugarcrm.candybean.automation.Candybean;
import com.sugarcrm.candybean.automation.control.VHook.Strategy;

import java.util.*;

/**
 * VSelect is a control that represents and allows for interaction with the SELECT element.
 *
 * @author cwarmbold
 */

public class VSelect extends VControl {

    private Select select;

    private boolean isMultiple;

	/**
	 * Instantiate a VSelect object.
	 *
	 * @param voodoo	  {@link Candybean} object for this run
	 * @param iface	  {@link VInterface} for this run
	 * @param strategy  {@link Strategy} used to search for element
	 * @param hook		  value of strategy to search for
	 * @throws Exception	 hook does not grab a SELECT
	 */
	public VSelect(Candybean voodoo, VInterface iface,
						Strategy strategy, String hook) throws Exception {
		this(voodoo, iface, new VHook(strategy, hook));
	}

	/**
	 * Instantiate a VSelect object.
	 *
	 * @param voodoo	{@link Candybean} object for this run
	 * @param iface	{@link VInterface} for this run
	 * @param hook		{@link VHook} used to search for this element
	 * @throws Exception	 hook does not grab a SELECT
	 */
	public VSelect(Candybean voodoo, VInterface iface, VHook hook)
		throws Exception {
		super(voodoo, iface, hook);
        select = new Select(super.we);
        isMultiple = select.isMultiple();
	}

    public boolean isMultiple() {
        return isMultiple;
    }

	/**
	 * Select all options that display text matching the argument.
     *
	 * @param value  text of the option to be selected
	 */
	public void select(String value) {
		voodoo.log.info("Selenium: selecting value '" + value  +  "' from control: " + this.toString());
		select.selectByVisibleText(value);
	}

    /**
     * Select an option by a given index.
     *
     * @param index index of option to be selected
     */
    public void select(int index) {
        List<WebElement> options = select.getOptions();
        voodoo.log.info("Selenium: selecting value '" + options.get(index).getText() + "' from control: " + this.toString());
        select.selectByIndex(index);
    }

    /**
     * Selects all the options in the list by adding to the current selection
     *
     * @param options
     */
    public void select(List<String> options) {
        for (String text : options) {
            select(text);
        }
    }

    /**
     * Selects all of the elements if the select support multiple selection
     */
    public void selectAll() {
        for (String option : getOptions()) {
            select(option);
        }
    }

    /**
     * Deselect an option by its visible text.
     *
     * @param text  text of the option to be deselected
     */
    public void deselect(String text) {
        voodoo.log.info("Selenium: deselecting value '" + text + "' from control: " + this.toString());
        select.deselectByVisibleText(text);
    }

    /**
     * Deselect an option by a given index.
     *
     * @param index index of option to be deselected
     */
    public void deselect(int index) {
        List<WebElement> options = select.getOptions();
        voodoo.log.info("Selenium: deselecting value '" + options.get(index).getText()  +  "' from control: " + this.toString());
        select.deselectByIndex(index);
    }

    /**
     * Deselects all of the options in the list
     *
     * @param options
     */
    public void deselect(ArrayList<String> options) {
        for (String text : options) {
            deselect(text);
        }
    }

    /**
     * Deselect all options.
     *
     */
    public void deselectAll() {
        voodoo.log.info("Selenium: deselecting all values from control: " + this.toString());
        select.deselectAll();
    }

    /**
     * Get the text of the first selected option.
     *
     * @return text of the first selected option in this select
     * (or the currently selected option in a normal select)
     */
    public String getFirstSelectedOption() {

        return select.getFirstSelectedOption().getText();
    }

    /**
     * Get a list of all the selected options.
     *
     * @return selected options
     */
    public List<String> getAllSelectedOptions() {
        List<WebElement> elementOptions = select.getAllSelectedOptions();
        List<String> textOptions = new ArrayList<>();
        for (WebElement we : elementOptions) {
            textOptions.add(we.getText());
        }
        return textOptions;
    }

    /**
     * Get a list of all the possible options.
     *
     * @return options
     */
    public List<String> getOptions() {
        List<WebElement> elementOptions = select.getOptions();
        List<String> textOptions = new ArrayList<>();
        for (WebElement we : elementOptions) {
            textOptions.add(we.getText());
        }
        return textOptions;
    }

    /**
     * Returns the boolean value of whether any options are selected.
     *
     * @return	boolean true if any options in the select element are selected
     */
    public boolean isSelected() {
        super.voodoo.log.info("Selenium: returns true if an option is selected: " + this.toString() + " is selected");
        return getAllSelectedOptions().size() > 0;
    }

    /**
     * Determine whether a certain option is selected.
     *
     * @param text
     * @return true if the option with given text is selected
     */
    public boolean isSelected(String text) {
        return getAllSelectedOptions().contains(text);
    }

    /**
     * Determine whether a certain option is selected at a certain index.
     *
     * @param index
     * @return true if the option at the index is selected
     */
    public boolean isSelected(int index) {
        return select.getOptions().get(index).isSelected();
    }

    /**
     * Determine whether multiple options are selected
     *
     * @param options
     * @return true if all the options passed in are selected
     */
    public boolean isSelected(List<String> options) {
        return isSelected(options, false);
    }

    /**
     * Determine whether a specific configuration of options is selected.
     * If the exclusive flag is set to true then this method will return false
     * if the selected options don't exactly match the list of options passed in.
     *
     * @param options
     * @return true if the options are selected
     */
    public boolean isSelected(List<String> options, boolean exclusive) {
        if (exclusive) {
            Set<String> selectedOptions = new HashSet<>(getAllSelectedOptions());
            Set<String> exactOptionsSet = new HashSet<>(options);
            return selectedOptions.equals(exactOptionsSet);
        } else {
            return getAllSelectedOptions().containsAll(options);
        }
    }

	@Override
	public String toString() {
		return "VSelect(" + super.toString() + ")";
	}
}
