package com.project.pageObjects;

import com.opencsv.CSVWriter;
import global.Const;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.common.CommonUtil;
import utils.common.LogUtil;
import utils.selenium.ElementAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegionHeaderPage extends BasePage {
    private static final String LOCATION_SEARCH_INPUT_ID = "LocationSearch_input";
    private static String SEARCH_RESULT_OPTION_XPATH = "//button[contains(@id,'LocationSearch_listbox')][text()='{value}']";
    private static final String SINGAPORE_OPTION = "Singapore, North, Singapore";
    private static String LOCAL_SUITE_HEADER_OVERFLOW_XPATH = "//div[contains(@id,'Localsuite')]//div[contains(@class,'overflow')]//a[@target='_self'][normalize-space()='{header}']";
    private static final String LOCAL_SUITE_HEADER_MORE_FORECASTS_XPATH = "//div[contains(@id,'Localsuite')]//div[contains(@class,'root')]/button";
    private static String LOCAL_SUITE_HEADER_MORE_FORECASTS_MENU_XPATH = "//div[contains(@id,'Localsuite')]//div[contains(@class,'root')]//a[@target='_self'][normalize-space()='{header}']";

    private static String TEN_DAY_INFORMATION = "//div[contains(@id,'DailyCard')]/section/div[contains(@class,'DisclosureList')]/details[contains(@id,'{index}')]";
    private static String TEN_DAY_HUMIDITY_SECTION_INFORMATION = "//*[@id='detailIndex0']//li[@data-testid='HumiditySection']//span[@data-testid='PercentageValue']";
    private static String TEN_DAY_HIGHTEMP_INFORMATION = "//*[@id='{index}']//div[@data-testid='detailsTemperature']/span[@data-testid='TemperatureValue']";
    private static String TEN_DAY_LOWTEMP_INFORMATION = "//*[@id='{index}']//div[@data-testid='detailsTemperature']/span[@data-testid='lowTempValue']";
    private static String TEN_DAY_DAY_PART_NAME_INFORMATION = "//*[@id='{index}']//h3[@data-testid='daypartName']";


    private static String TEN_DAY_DAY_PART_NAME_DETAILS_PART_DAY_OR_NIGHT_INFORMATION = "//*[@id='{index}']//h3[contains(@class,'daypartName')][contains(normalize-space(.),'{partDayOrNight}')]";
    private static String TEN_DAY_HUMIDITY_SECTION = "/parent::div/following-sibling::div[1]//li[@data-testid='HumiditySection']//span[@data-testid='PercentageValue']";
    private static String TEN_DAY_TEMP_DETAILS = "/following-sibling::div//span[@data-testid='TemperatureValue']";

    private static String ARROWUP_BUTTON_INDEX = "//details[@id='{index}']/summary/div";

    @FindBy(how = How.ID, using = LOCATION_SEARCH_INPUT_ID)
    private WebElement locationSearchInput;

    public RegionHeaderPage(WebDriver driver) {
        super(driver);
    }

    public Boolean isLocationSearchInputDisplayed() {
        CommonUtil.waitForElement(locationSearchInput, Const.EXCEPTION_CONDITIONS.CLICKABLE, 40, 2);
        return locationSearchInput.isDisplayed();
    }

    public void inputSearch(String searchValue) {
        ElementAction.typeElement(locationSearchInput, searchValue, "Location Search Input");
    }

    public void clickSearchOption(String expectOption) {
        String option = "";
        switch (expectOption) {
            case SINGAPORE_OPTION:
                option = SEARCH_RESULT_OPTION_XPATH.replace("{value}", SINGAPORE_OPTION);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + option);
        }
        ElementAction.clickElementByXpath(option, "Search Singapore Option");
    }

    public void clickLocalSuiteHeader(String expectHeader) {
        String header = LOCAL_SUITE_HEADER_OVERFLOW_XPATH.replace("{header}", expectHeader);
        ElementAction.clickElementByXpath(header, "Local Suite Header");
    }

    public void getTemperatureAndHumidityOf10DaysWeather(int days) {
        try {
            List<String> partDays = new ArrayList<>();
            partDays.add("Day");
            partDays.add("Night");
            String partName = null, datePartName = null, temp = null, humidity = null, indexRow = null;
            String index = "";
            File file = new File("output/generate10daysweather.csv");
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);
            // create CSVWriter object filewriter object as parameter
            // create CSVWriter with '|' as separator
            CSVWriter writer = new CSVWriter(outputfile, '|',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            String[] header = {"Date|PartDay", "Temp", "Humidity"};
            writer.writeNext(header);
            ArrayList<String> listForCsv = new ArrayList<>();
            for (int i = 0; i < days - 1; i++) {
                index = "detailIndex" + i;
                indexRow = ARROWUP_BUTTON_INDEX.replace("{index}", index);
                String tempValue = TEN_DAY_DAY_PART_NAME_DETAILS_PART_DAY_OR_NIGHT_INFORMATION.replace("{index}", index);
                String dayTemp = TEN_DAY_HIGHTEMP_INFORMATION = TEN_DAY_HIGHTEMP_INFORMATION.replace("{index}", index);
                if (i != 0) {
                    CommonUtil.think(2);
                    ElementAction.clickElementByXpath(indexRow, "Arrow Up button of Index to Open");
                }
                for (String partDay : partDays) {
                    if (partDay.equals("Day") && ElementAction.getTextElementByXpath(dayTemp).equals("")) {
                        LogUtil.info(String.format("%s index %d", partDay, i));
                    } else {
                        tempValue = tempValue.replace("{partDayOrNight}", partDay);
                        partName = ElementAction.getTextElementByXpath(tempValue);
                        temp = ElementAction.getTextElementByXpath(tempValue + TEN_DAY_TEMP_DETAILS);
                        humidity = ElementAction.getTextElementByXpath(tempValue + TEN_DAY_HUMIDITY_SECTION);
                        LogUtil.info(String.format("%s %d days from today , PartName: %s, Temp: %s, Humidity: %s \n", partDay, i, partName, temp, humidity));
                        listForCsv = new ArrayList<>();
                        listForCsv.add(partName);
                        listForCsv.add(temp);
                        listForCsv.add(humidity);
                        writer.writeNext(listForCsv.stream().toArray(String[]::new));
                        if (tempValue.contains("Day")) {
                            tempValue = tempValue.replace("Day", "{partDayOrNight}");
                        } else if (tempValue.contains("Night")) {
                            tempValue = tempValue.replace("Night", "{partDayOrNight}");
                        }
                    }
                }
                CommonUtil.think(2);
                ElementAction.clickElementByXpath(indexRow, "Arrow Up button of Index to Close");
            }

            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
