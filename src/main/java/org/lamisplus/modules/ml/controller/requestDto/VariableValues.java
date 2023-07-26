package org.lamisplus.modules.ml.controller.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariableValues {
    @JsonProperty("age")
    int age;
    @JsonProperty("sex_M")
    int sexM;
    @JsonProperty("sex_F")
    int sexF;
    @JsonProperty("first_time_visit_Y")
    int firstTimeVisitY;
    @JsonProperty("referred_from_Self")
    int referredFromSelf;
    @JsonProperty("referred_from_Other")
    int referredFromOther;
    @JsonProperty("marital_status_Married")
    int maritalStatusMarried;
    @JsonProperty("marital_status_Divorced")
    int maritalStatusDivorced;
    @JsonProperty("marital_status_Widowed")
    int maritalStatusWidowed;
    @JsonProperty("session_type_Individual")
    int sessionTypeIndividual;
    @JsonProperty("previously_tested_hiv_negative_Missing")
    int previouslyTestedHivNegativeMissing;
    @JsonProperty("previously_tested_hiv_negative_TRUE.")
    int previouslyTestedHivNegativeTRUE;
    @JsonProperty("client_pregnant_X0")
    int clientPregnantX0;
    @JsonProperty("hts_setting_Others")
    int htsSettingOthers;
    @JsonProperty("hts_setting_Outreach")

    int htsSettingOutreach;
    @JsonProperty("hts_setting_Other")

    int htsSettingOther;
    @JsonProperty("tested_for_hiv_before_within_this_year_NotPreviouslyTested")
    int testedForHivBeforeWithinThisYearNotPreviouslyTested;
    @JsonProperty("tested_for_hiv_before_within_this_year_PreviouslyTestedNegative")

    int testedForHivBeforeWithinThisYearPreviouslyTestedNegative;
    @JsonProperty("tested_for_hiv_before_within_this_year_PreviouslyTestedPositiveInHIVCare")
    int testedForHivBeforeWithinThisYearPreviouslyTestedPositiveInHIVCare;
    @JsonProperty("tested_for_hiv_before_within_this_year_PreviouslyTestedPositiveNotInHIVCare")
    int testedForHivBeforeWithinThisYearPreviouslyTestedPositiveNotInHIVCare;

}
