package org.lamisplus.modules.ml.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class VariableValues {
    @JsonProperty("age")
    private final int age;
    @JsonProperty("sex_M")
    private final int sexM;
    @JsonProperty("sex_F")
    private final int sexF;
    @JsonProperty("first_time_visit_Y")
    private final int firstTimeVisitY;
    @JsonProperty("referred_from_Self")
    private final int referredFromSelf;
    @JsonProperty("referred_from_Other")
    private final int referredFromOther;
    @JsonProperty("marital_status_Married")
    private final int maritalStatusMarried;
    @JsonProperty("marital_status_Divorced")
    private final int maritalStatusDivorced;
    @JsonProperty("marital_status_Widowed")
    private final int maritalStatusWidowed;
    @JsonProperty("session_type_Individual")
    private final int sessionTypeIndividual;
    @JsonProperty("previously_tested_hiv_negative_Missing")
    private final int previouslyTestedHivNegativeMissing;
    @JsonProperty("previously_tested_hiv_negative_TRUE.")
    private final int previouslyTestedHivNegativeTRUE;
    @JsonProperty("client_pregnant_X0")
    private final int clientPregnantX0;
    @JsonProperty("hts_setting_Others")
    private final int htsSettingOthers;
    @JsonProperty("hts_setting_Outreach")
    private final int htsSettingOutreach;
    @JsonProperty("hts_setting_Other")
    private final  int htsSettingOther;
    @JsonProperty("tested_for_hiv_before_within_this_year_NotPreviouslyTested")
    private final int testedForHivBeforeWithinThisYearNotPreviouslyTested;
    @JsonProperty("tested_for_hiv_before_within_this_year_PreviouslyTestedNegative")
    private final int testedForHivBeforeWithinThisYearPreviouslyTestedNegative;
    @JsonProperty("tested_for_hiv_before_within_this_year_PreviouslyTestedPositiveInHIVCare")
    private final int testedForHivBeforeWithinThisYearPreviouslyTestedPositiveInHIVCare;
    @JsonProperty("tested_for_hiv_before_within_this_year_PreviouslyTestedPositiveNotInHIVCare")
    private final int testedForHivBeforeWithinThisYearPreviouslyTestedPositiveNotInHIVCare;

}
