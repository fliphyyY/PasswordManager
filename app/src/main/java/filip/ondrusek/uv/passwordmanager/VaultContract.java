package filip.ondrusek.uv.passwordmanager;

import android.provider.BaseColumns;

public class VaultContract {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VaultEntry.TABLE_NAME + " (" +
                    VaultEntry._ID + " INTEGER PRIMARY KEY," +
                    VaultEntry.COLUMN_NAME_NAME + " TEXT," +
                    VaultEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    VaultEntry.COLUMN_NAME_PASSWORD + " TEXT)"; /*+
                    VaultEntry.COLUMN_NAME_COUGH + " TEXT," +
                    VaultEntry.COLUMN_NAME_BREATHING + " TEXT," +
                    VaultEntry.COLUMN_NAME_FATIGUE + " TEXT," +
                    VaultEntry.COLUMN_NAME_MUSCLE_BODY_ACHES + " TEXT," +
                    VaultEntry.COLUMN_NAME_HEADACHE + " TEXT," +
                    VaultEntry.COLUMN_NAME_TASTE_SMELL_LOSS + " TEXT," +
                    VaultEntry.COLUMN_NAME_SORE_THROAT + " TEXT," +
                    VaultEntry.COLUMN_NAME_CONGESTION_RUNNY_NOSE + " TEXT," +
                    VaultEntry.COLUMN_NAME_NAUSEA_VOMITING + " TEXT," +
                    VaultEntry.COLUMN_NAME_DIARRHEA + " TEXT," +
                    VaultEntry.COLUMN_NAME_CLOSE_CONTACT + " TEXT," +
                    VaultEntry.COLUMN_NAME_MUNICIPALITY + " TEXT)";*/
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + VaultEntry.TABLE_NAME;

    private VaultContract() {
    }

    public static class VaultEntry implements BaseColumns {
        public static final String TABLE_NAME = "vault";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_USERNAME = "username";
        /*public static final String COLUMN_NAME_SYMPTOMS_START_DATE = "symptoms_start_date";
        public static final String COLUMN_NAME_FEVER_CHILLS = "fever_chills";
        public static final String COLUMN_NAME_COUGH = "cough";
        public static final String COLUMN_NAME_BREATHING = "breathing";
        public static final String COLUMN_NAME_FATIGUE = "fatigue";
        public static final String COLUMN_NAME_MUSCLE_BODY_ACHES = "muscle_body_aches";
        public static final String COLUMN_NAME_HEADACHE = "headache";
        public static final String COLUMN_NAME_TASTE_SMELL_LOSS = "taste_smell_loss";
        public static final String COLUMN_NAME_SORE_THROAT = "sore_throat";
        public static final String COLUMN_NAME_CONGESTION_RUNNY_NOSE = "congestion_runny_nose";
        public static final String COLUMN_NAME_NAUSEA_VOMITING = "nausea_vomiting";
        public static final String COLUMN_NAME_DIARRHEA = "diarrhea";
        public static final String COLUMN_NAME_CLOSE_CONTACT = "close_contact";
        public static final String COLUMN_NAME_MUNICIPALITY = "municipality";*/
    }
}
