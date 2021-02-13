/***************************************************************************************
 *  ACCOUNT TYPES                                                                      *
 *  TRANSACTIONAL determines if a client can use the card to pay at a POS or draw      *
 *     money at an ATM on the specified account                                        *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  ACCOUNT_TYPE;
CREATE TABLE ACCOUNT_TYPE (
  ACCOUNT_TYPE_CODE VARCHAR(10) NOT NULL PRIMARY KEY,
  DESCRIPTION       VARCHAR(50) NOT NULL,
  TRANSACTIONAL     boolean not null
);

/*
****************************************************************************************
 *  CLIENT TYPE                                                                        *
 ***************************************************************************************
 */
DROP TABLE  IF EXISTS  CLIENT_TYPE;
CREATE TABLE CLIENT_TYPE (
  CLIENT_TYPE_CODE VARCHAR(2)   NOT NULL PRIMARY KEY,
  DESCRIPTION      VARCHAR(255) NOT NULL
);

/***************************************************************************************
 *  CLIENT SUB TYPE                                                                        *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  CLIENT_SUB_TYPE;
CREATE TABLE CLIENT_SUB_TYPE (
  CLIENT_SUB_TYPE_CODE VARCHAR(4)   NOT NULL PRIMARY KEY,
  CLIENT_TYPE_CODE     VARCHAR(2)   NOT NULL REFERENCES CLIENT_TYPE (CLIENT_TYPE_CODE),
  DESCRIPTION          VARCHAR(255) NOT NULL
);

/***************************************************************************************
 *  List of currency codes for use in the formatting test                              *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  CURRENCY;
CREATE TABLE CURRENCY (
  CURRENCY_CODE  VARCHAR(3)   NOT NULL PRIMARY KEY,
  DECIMAL_PLACES INTEGER      NOT NULL,
  DESCRIPTION    VARCHAR(255) NOT NULL
);

/***************************************************************************************
 * Currency conversion rats                                                            *
 * The CONVERSION_INDICATOR specifies if the foreign amount should be multiplied ('*') *
 * or divided when converting to the local amount.                                     *
 *                                                                                     *
 *  i.e. USD 12 = 12 * 11.6167                                                         *
 *              = 139.40 ZAR                                                           *
 *                                                                                     *
 *                                                                                     *
 *       AUD 12 = 12 / 0.1134                                                          *
 *              = 105.82 ZAR                                                           *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  CURRENCY_CONVERSION_RATE;
CREATE TABLE CURRENCY_CONVERSION_RATE (
  CURRENCY_CODE        VARCHAR(3)     NOT NULL PRIMARY KEY REFERENCES CURRENCY (CURRENCY_CODE),
  CONVERSION_INDICATOR VARCHAR(1)     NOT NULL,
  RATE                 DECIMAL(18, 8) NOT NULL
);

/***************************************************************************************
 *  Table containing the types of "material" in which denominations are issued         *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  DENOMINATION_TYPE;
CREATE TABLE DENOMINATION_TYPE (
  DENOMINATION_TYPE_CODE VARCHAR(1)   NOT NULL PRIMARY KEY,
  DESCRIPTION            VARCHAR(255) NOT NULL
);

/***************************************************************************************
 *  Table containing all the denominations for the purposes of the test                *
 *  Denomination types are either C or N where C indicates a "Coin" and N indicates a  *
 *  "Note"*                                                                            *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  DENOMINATION;
CREATE TABLE DENOMINATION (
  DENOMINATION_ID        INTEGER NOT NULL PRIMARY KEY,
  VALUE                  DECIMAL NOT NULL,
  DENOMINATION_TYPE_CODE VARCHAR(1) REFERENCES DENOMINATION_TYPE (DENOMINATION_TYPE_CODE)
);

/***************************************************************************************
 *  CLIENT DATA                                                                      *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  CLIENT;
CREATE TABLE CLIENT (
  CLIENT_ID            INTEGER NOT NULL PRIMARY KEY,
  TITLE                VARCHAR(10),
  NAME                 VARCHAR(255) NOT NULL,
  SURNAME              VARCHAR(100),
  DOB                  DATE         NOT NULL,
  CLIENT_SUB_TYPE_CODE VARCHAR(4)   NOT NULL REFERENCES CLIENT_SUB_TYPE (CLIENT_SUB_TYPE_CODE)
);

/***************************************************************************************
 *  CLIENT ACCOUNT BALANCES                                                            *
 *  SVGS and CFCA list positive account balances                                       *
 *  CHQ accounts allow being overdrawn and can have either a positive or               *
 *       negative balance                                                              *
 *  CCRD lists the current *available* balance on a credit card account, card limits   *
 *       are stored on the CREDIT_CARD_LIMIT table and lists the maximum credit amount *
 *       credit card accounts do now allow being overdrawn                             *
 *  PLOAN and HLOAN list outstanding balances on the loan amounts as negative amounts  *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  CLIENT_ACCOUNT;
CREATE TABLE CLIENT_ACCOUNT (
  CLIENT_ACCOUNT_NUMBER VARCHAR(10) NOT NULL PRIMARY KEY,
  CLIENT_ID             INTEGER     NOT NULL REFERENCES CLIENT (CLIENT_ID),
  ACCOUNT_TYPE_CODE     VARCHAR(10) NOT NULL REFERENCES ACCOUNT_TYPE (ACCOUNT_TYPE_CODE),
  CURRENCY_CODE         VARCHAR(3)  NOT NULL REFERENCES CURRENCY (CURRENCY_CODE),
  DISPLAY_BALANCE       NUMERIC(18, 3)
);

/*
****************************************************************************************
 *  CREDIT CARD LIMITS                                                                 *
 *  Lists the credit amount that a client is allowed to access on the specified        *
 *  Credit Card account                                                                *
 ***************************************************************************************
 */
DROP TABLE  IF EXISTS  CREDIT_CARD_LIMIT;
CREATE TABLE CREDIT_CARD_LIMIT (
  CLIENT_ACCOUNT_NUMBER VARCHAR(10)    NOT NULL PRIMARY KEY REFERENCES CLIENT_ACCOUNT (CLIENT_ACCOUNT_NUMBER),
  ACCOUNT_LIMIT         DECIMAL(18, 3) NOT NULL
);

/***************************************************************************************
 *  ATM                                                                                *
 *  Lists the ID and location of each ATM                                              *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  ATM;
CREATE TABLE ATM (
  ATM_ID   INTEGER NOT NULL PRIMARY KEY,
  NAME     VARCHAR(10)  NOT NULL UNIQUE,
  LOCATION VARCHAR(255) NOT NULL
);


/***************************************************************************************
 *  ATM ALLOCATION                                                                     *
 *  Stores the number of notes per denomination available to each ATM                  *
 ***************************************************************************************/
DROP TABLE  IF EXISTS  ATM_ALLOCATION;
CREATE TABLE ATM_ALLOCATION (
  ATM_ALLOCATION_ID INTEGER NOT NULL PRIMARY KEY,
  ATM_ID            INTEGER NOT NULL REFERENCES ATM (ATM_ID),
  DENOMINATION_ID   INTEGER NOT NULL REFERENCES DENOMINATION (DENOMINATION_ID),
  COUNT             INTEGER NOT NULL
);
