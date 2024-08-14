package org.lamisplus.modules.ml.controller.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariableValues {

    @JsonProperty("gender_female")
    int gender_female;
    @JsonProperty("gender_male")
    int gender_male;
    @JsonProperty("marital_status_divorced")
    int marital_status_divorced;
    @JsonProperty("marital_status_married")
    int marital_status_married;
    @JsonProperty("marital_status_other")
    int marital_status_other;
    @JsonProperty("marital_status_single")
    int marital_status_single;
    @JsonProperty("target_group_CHILDREN_OF_KP")
    int target_group_CHILDREN_OF_KP;
    @JsonProperty("target_group_FSW")
    int target_group_FSW;
    @JsonProperty("target_group_GEN_POP")
    int target_group_GEN_POP;
    @JsonProperty("target_group_MSM")
    int target_group_MSM;
    @JsonProperty("target_group_other")
    int target_group_other;
    @JsonProperty("target_group_PWID")
    int target_group_PWID;
    @JsonProperty("target_group_SEXUAL_PARTNER")
    int target_group_SEXUAL_PARTNER;
    @JsonProperty("referred_from_Community_Mobilization")
    int referred_from_Community_Mobilization;
    @JsonProperty("referred_from_OPD")
    int referred_from_OPD;
    @JsonProperty("referred_from_Others")
    int referred_from_Others;
    @JsonProperty("referred_from_Private_Commercial_Health_facility")
    int referred_from_Private_Commercial_Health_facility;
    @JsonProperty("referred_from_Self")
    int referred_from_Self;
    @JsonProperty("testing_setting_CPMTCT")
    int testing_setting_CPMTCT;
    @JsonProperty("testing_setting_CT")
    int testing_setting_CT;
    @JsonProperty("testing_setting_OPD")
    int testing_setting_OPD;
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
    @JsonProperty("testing_setting_TB")
    int testing_setting_TB;
    @JsonProperty("testing_setting_WARD")
    int testing_setting_WARD;
    @JsonProperty("age")
    int age;
    @JsonProperty("first_time_visit")
    int first_time_visit;
    @JsonProperty("index_client")
    int index_client;
    @JsonProperty("previously_tested")
    int previously_tested;
    @JsonProperty("clientPregnant")
    int clientPregnant;
    @JsonProperty("stiInLast3Months")
    int stiInLast3Months;
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
    @JsonProperty("sti_symptoms")
    int sti_symptoms;
    @JsonProperty("tb_symptoms")
    int tb_symptoms;
}
