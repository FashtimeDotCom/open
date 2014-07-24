/*==============================================================*/
/* Table: OP_PARTNER                                            */
/*==============================================================*/
create table OP_PARTNER 
(
   ID                   NUMBER(13)                     not null,
   PARTNER_NAME         VARCHAR2(200)                  null,
   DESCRIPTION          VARCHAR2(2000)                 null,
   STATUS               VARCHAR2(10)                   null,
   MD5_KEY              VARCHAR2(100)                  null,
   IP_LIST              VARCHAR2(2000)                 null,
   CREATE_BY            VARCHAR2(100)                  null,
   LAST_UPDATE_TIME     TIMESTAMP                      null,
   REMARK               VARCHAR2(2000)                 null,
   constraint PK_OP_PARTNER primary key (ID)
);

comment on table OP_PARTNER is 
'������Ӧ���б�';

comment on column OP_PARTNER.PARTNER_NAME is 
'������Ӧ������';

comment on column OP_PARTNER.DESCRIPTION is 
'������Ӧ������';

comment on column OP_PARTNER.STATUS is 
'״̬  1 ����  ����δ����';

comment on column OP_PARTNER.MD5_KEY is 
'key';

comment on column OP_PARTNER.IP_LIST is 
'IP��ַ�б�';

/*==============================================================*/
/* Table: OP_SERVICE                                            */
/*==============================================================*/
create table OP_SERVICE 
(
   ID                   NUMBER(13)                     not null,
   SERVICE_NAME         VARCHAR2(100)                  null,
   METHOD_NAME          VARCHAR2(100)                  null,
   URL                  VARCHAR2(1000)                 null,
   DESCRIPTION          VARCHAR2(2000)                 null,
   IS_PROTECT           VARCHAR2(10)                   null,
   STATUS               VARCHAR2(10)                   null,
   CREATE_BY            VARCHAR2(100)                  null,
   LAST_UPDATE_TIME     TIMESTAMP                      null,
   REMARK               VARCHAR2(2000)                 null,
   constraint PK_OP_SERVICE primary key (ID)
);

comment on table OP_SERVICE is 
'����ƽ̨�����';

comment on column OP_SERVICE.IS_PROTECT is 
'1��������Ҫ��Ȩ
��������Ҫ';

comment on column OP_SERVICE.STATUS is 
'1��ͨ ����δ��ͨ';

/*==============================================================*/
/* Table: PARTNER_SERVICE                                       */
/*==============================================================*/
create table PARTNER_SERVICE 
(
   ID                   NUMBER(13)                     not null,
   PARTNER_ID           NUMBER(13)                     null,
   SERVICE_ID           NUMBER(13)                     null,
   LAST_UPDATE_TIME     TIMESTAMP                      null,
   constraint PK_PARTNER_SERVICE primary key (ID)
);

comment on table PARTNER_SERVICE is 
'������Ӧ��Ȩ�ޱ�';



create sequence SEQ_PARTNER
start with 10000
 maxvalue 99999
 minvalue 10000
cycle;


create sequence SEQ_OP_SERVICE
start with 10000
 maxvalue 99999
 minvalue 10000
cycle;

create sequence SEQ_PARTNER_SERVICE
start with 10000
 maxvalue 99999
 minvalue 10000
cycle;
