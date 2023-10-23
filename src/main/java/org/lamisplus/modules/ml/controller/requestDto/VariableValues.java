package org.lamisplus.modules.ml.controller.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariableValues {
    @JsonProperty("age")
    int age;
    @JsonProperty("gender_female")
    int gender_female;
    @JsonProperty("gender_male")
    int gender_male;
    @JsonProperty("first_time_visit")
    int first_time_visit;
    @JsonProperty("target_group_CHILDREN_OF_KP")
    int target_group_CHILDREN_OF_KP;
    @JsonProperty("target_group_FSW")
    int target_group_FSW;
    @JsonProperty("target_group_General_pop")
    int target_group_General_pop;
    @JsonProperty("target_group_MSM")
    int target_group_MSM;
    @JsonProperty("target_group_PWID")
    int target_group_PWID;
    @JsonProperty("referred_from_43")
    int referred_from_43;
    @JsonProperty("referred_from_50")
    int referred_from_50;
    @JsonProperty("referred_from_OTHERS")
    int referred_from_OTHERS;
    @JsonProperty("marital_status_other")
    int marital_status_other;
    @JsonProperty("marital_status_single")

    int marital_status_single;
    @JsonProperty("testing_setting_CT")

    int testing_setting_CT;
    @JsonProperty("testing_setting_OTHERS")
    int testing_setting_OTHERS;
    @JsonProperty("testing_setting_OUTREACH")

    int testing_setting_OUTREACH;
    @JsonProperty("testing_setting_SNT")
    int testing_setting_SNT;
    @JsonProperty("testing_setting_STANDALONE_HTS")
    int testing_setting_STANDALONE_HTS;
    @JsonProperty("testing_setting_STI")
    int testing_setting_STI;
    @JsonProperty("stiInLast3Months")
    int stiInLast3Months;
    @JsonProperty("index_client")
    int index_client;
    @JsonProperty("previously_tested")
    int previously_tested;
    @JsonProperty("everHadSexualIntercourse")
    int everHadSexualIntercourse;
    @JsonProperty("bloodTransfusionInLast3Months")
    int bloodTransfusionInLast3Months;
    @JsonProperty("moreThan1SexPartnerDuringLast3Months")
    int moreThan1SexPartnerDuringLast3Months;
    @JsonProperty("unprotectedSexWithCasualPartnerInLast3Months")
    int unprotectedSexWithCasualPartnerInLast3Months;
    @JsonProperty("unprotectedSexWithRegularPartnerInLast3Months")
    int unprotectedSexWithRegularPartnerInLast3Months;
    @JsonProperty("tb_symptoms")
    int tb_symptoms;
    @JsonProperty("sti_symptoms")
    int sti_symptoms;
}
