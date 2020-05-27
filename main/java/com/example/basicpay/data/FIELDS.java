package com.example.basicpay.data;

import java.util.HashMap;
import java.util.Map;

enum FIELDS {
    // |Field title                      |no     |type        |len         |fixed       |format|
    F1_Bitmap                           (1,     "b",  8,  true,  0),
    F2_PAN                              (2,     "n",  19, false, 2),
    F3_ProcessCode                      (3,     "n",  6,  true,  0),
    F4_AmountTransaction                (4,     "n",  12, true,  0),
    F5_AmountSettlement                 (5,     "n",  12, true,  0),
    F6_AmountCardholder                 (6,     "n",  12, true,  0),
    F7_TranDateTime                     (7,     "n",  10, true,  0),
    F8_AmountCardholder_BillingFee      (8,     "n",  8,  true,  0),
    F9_Conv_Rate_Settlement             (9,     "n",  8,  true,  0),
    F10_Conv_Rate_Cardholder            (10,    "n",  8,  true,  0),
    F11_Trace_No                        (11,    "n",  6,  true,  0),
    F12_LocalTime                       (12,    "n",  6,  true,  0),
    F13_LocalDate                       (13,    "n",  4,  true,  0),
    F14_ExpirationDate                  (14,    "n",  4,  true,  0),
    F15_SettlementDate                  (15,    "n",  4,  true,  0),
    F16_Curr_Conv_Date                  (16, "n",   4,    true,  0),
    F17_CaptureDate                     (17, "n",   4,    true,  0),
    F18_MerchantType                    (18, "n",   4,    true,  0),
    F19_AcquiringInstitution            (19, "n",   3,    true,  0),
    F20_PANExtended                     (20, "n",   3,    true,  0),
    F21_ForwardingInstitution           (21, "n",   3,    true,  0),
    F22_EntryMode                       (22, "n",   3,    true,  0),
    F23_PANSequence                     (23, "n",   3,    true,  0),
    F24_NII_FunctionCode                (24, "n",   3,    true,  0),
    F25_POS_ConditionCode               (25, "n",   2,    true,  0),
    F26_POS_CaptureCode                 (26, "n",   2,    true,  0),
    F27_AuthIdResponseLength            (27, "n",   1,    true,  0),
    F28_Amount_TransactionFee           (28, "x+n", 8,    true,  0),
    F29_Amount_SettlementFee            (29, "x+n", 8,    true,  0),
    F30_Amount_Tran_Proc_Fee            (30, "x+n", 8,    true,  0),
    F31_Amount_Sett_Proc_Fee            (31, "x+n", 8,    true,  0),
    F32_AcqrInstIdCode                  (32, "n",   11,   false, 2),
    F33_Forw_Inst_IdCode                (33, "n",   11,   false, 2),
    F34_PAN_Extended                    (34, "ns",  28,   false, 2),
    F35_Track2                          (35, "z",   37,   false, 2),
    F36_Track3                          (36, "z",   104,  false, 3),
    F37_RRN                             (37, "an",  12,   true,  0),
    F38_AuthIdResponse                  (38, "an",  6,    true,  0),
    F39_ResponseCode                    (39, "an",  2,    true,  0),
    F40_ServiceRestrictionCode          (40, "an",  3,    true,  0),
    F41_CA_TerminalID                   (41, "ans", 8,    true,  0),
    F42_CA_ID                           (42, "ans", 15,   true,  0),
    F43_CardAcceptorInfo                (43, "ans", 40,   true,  0),
    F44_AddResponseData                 (44, "an",  25,   false, 2),
    F45_Track1                          (45, "an",  76,   false, 2),
    F46_AddData_ISO                     (46, "an",  999,  false, 3),
    F47_AddData_National                (47, "an",  999,  false, 3),
    F48_AddData_Private                 (48, "an",  999,  false, 3),
    F49_Curr_Code_Tran                  (49, "n",   3,    true,  0),
    F50_Curr_Code_Settlement            (50, "n",   3,    true,  0),
    F51_Curr_Code_Cardholder            (51, "n",   3,    true,  0),
    F52_PIN                             (52, "b",   8,    true,  0),
    F53_SecurityControlInfo             (53, "n",   16,   true,  0),
    F54_AddAmount                       (54, "an",  120,  false, 3),
    F55_ICC                             (55, "ans", 999,  false, 3),
    F56_Reserved_ISO                    (56, "ans", 999,  false, 3),
    F57_Reserved_National               (57, "ans", 999,  false, 3),
    F58_National_Cond_Code              (58, "ans", 999,  false, 3),
    F59_Reserved_National               (59, "ans", 999,  false, 3),
    F60_Reserved_National               (60, "ans", 999,  false, 3),
    F61_Reserved_Private                (61, "ans", 999,  false, 3),
    F62_Reserved_Private                (62, "ans", 999,  false, 3),
    F63_Reserved_Private                (63, "ans", 999,  false, 3),
    F64_MAC                             (64, "b",   16,   true,  0),
    F70_Net_Mgmt_Code                   (70, "n",   3,    true,  0),
    F96_Msg_Sec_Code                    (96, "an",  8,    true,  0),
    F109_Sender_Data                    (109,"ans", 999,  false, 3),
    F110_Receiver_Data                  (110,"ans", 999,  false, 3);

    private final int no;
    private final String type;
    private final int length;
    private final boolean fixed;
    private final int format;

    FIELDS(int no, String type, int length, boolean fixed, int format) {
        this.no = no;
        this.type = type;
        this.length = length;
        this.fixed = fixed;
        this.format = format;
    }

    private static Map<Integer, FIELDS> map = new HashMap<Integer, FIELDS>();

    static {
        for (FIELDS field : FIELDS.values()) {
            map.put(field.getNo(), field);
        }
    }

    public int getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public boolean isFixed() {
        return fixed;
    }

    public int getFormat() {
        return format;
    }

    public static FIELDS valueOf(int no) {
        return map.get(no);
    }
}
