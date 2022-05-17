<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://docs.simplicite.io//logos/logo250.png)
* * *

`Qualification` module definition
=================================



`QualExercise` business object definition
-----------------------------------------

Exercise

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `qualExId`                                                   | char(100)                                | yes*     |           |          | -                                                                                |
| `qualExType`                                                 | enum(40) using `QUALEXTYPE` list         | yes      | yes       |          | -                                                                                |
| `qualExQuestion`                                             | text(10000)                              | yes      | yes       |          | -                                                                                |
| `qualExAnswerType`                                           | enum(100) using `QUALEXANWSERTYPE` list  | yes      | yes       |          | -                                                                                |
| `qualExAnswerText`                                           | text(10000)                              |          | yes       |          | -                                                                                |
| `qualExImage`                                                | document                                 |          | yes       |          | -                                                                                |
| `qualExDifficulty`                                           | enum(100) using `QUALEXDIFFICULTY` list  | yes      | yes       |          | -                                                                                |
| `qualExChoicesEnumeration`                                   | text(10000)                              |          | yes       |          | -                                                                                |
| `qualExAnswerEnumeration`                                    | char(200)                                |          | yes       |          | -                                                                                |
| `qualExRefenum`                                              | enum(100) using `QUAL_REF_ENUM_CHOICES` list |          | yes       |          | -                                                                                |
| `qualExLangue`                                               | enum(100) using `QUALEXLANGUE` list      |          | yes       |          | -                                                                                |

### Lists

* `QUALEXTYPE`
    - `UML` UML
    - `JAVA` JAVA
    - `JS` JS
    - `SQL` SQL
    - `SIM_BASE` Simplicité - Notions de base
    - `SIM_CONCEPTION` Simplicité - Conception
    - `SIM_OPERATION` Simplicité - Opération
    - `SIM_INTEGRATION` Simplicité - Intégration
    - `SIM_REX` Simplicité - Retour Formation
    - `SIM_CERTIF_COMP` Certification - Concepts généraux
    - `SIM_CERTIF_PARAM` Certification - Paramétrage
    - `SIM_CERTIF_DEV` Certification - Developpement Java
    - `SIM_CERTIF_DOC` Certification - Documentation
    - `SIM_CERTIF_INSTAL_EXP` Certification - Installation - Exploitation
    - `SIM_CERTIF_BEHAVE` Certification - Comportement
    - `SIM_CERTIF_HOOKS` Certification - Hooks
    - `NA` Non applicable
* `QUALEXANWSERTYPE`
    - `TXT` Texte
    - `ENUM` Énuméré
    - `MULTI_ENUM` Énuméré multiple
    - `DIAG` Diagramme
* `QUALEXDIFFICULTY`
    - `1` Niveau 1
    - `2` Niveau 2
    - `3` Niveau 3
    - `NA` Non applicable
* `QUAL_REF_ENUM_CHOICES`
    - `Certification - Comportement-Niveau 1-303_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-305_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-307_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-309_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-311_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-313_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-315_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-317_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-135_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-137_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-139_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-141_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-143_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-277_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-279_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-281_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-283_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-285_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-287_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-289_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-291_REF_ENUM` 
    - `Certification - Hooks-Niveau 1-275_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-319_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-321_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-323_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-325_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-327_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-329_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-145_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-147_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-149_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-151_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-153_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-155_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-157_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-159_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-161_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-163_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-165_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-167_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-169_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-171_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-173_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-175_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-177_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-179_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-181_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-183_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-185_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-187_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-189_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-191_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-193_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-195_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-197_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-199_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-201_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-203_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-205_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-207_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-209_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-211_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-213_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-215_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-217_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-219_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-221_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-223_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-225_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-227_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-229_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-231_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-233_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-235_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-237_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-239_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-241_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-243_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-245_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-247_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-249_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-251_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-253_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-255_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-257_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-259_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-261_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-263_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-265_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-267_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-269_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-271_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-273_REF_ENUM` 
    - `JAVA-1-351_REF_ENUM` 
    - `SIM_BASE-1-445_REF_ENUM` 
    - `SIM_BASE-1-447_REF_ENUM` 
    - `SIM_BASE-1-449_REF_ENUM` 
    - `SIM_BASE-1-451_REF_ENUM` 
    - `SIM_BASE-1-453_REF_ENUM` 
    - `SIM_BASE-1-455_REF_ENUM` 
    - `SIM_BASE-1-457_REF_ENUM` 
    - `SIM_BASE-1-459_REF_ENUM` 
    - `SIM_BASE-1-461_REF_ENUM` 
    - `SIM_BASE-NA-345_REF_ENUM` 
    - `SIM_BASE-NA-353_REF_ENUM` 
    - `SIM_BASE-NA-355_REF_ENUM` 
    - `SIM_BASE-NA-357_REF_ENUM` 
    - `SIM_BASE-NA-359_REF_ENUM` 
    - `SIM_BASE-NA-361_REF_ENUM` 
    - `SIM_BASE-NA-363_REF_ENUM` 
    - `SIM_BASE-NA-365_REF_ENUM` 
    - `SIM_BASE-NA-367_REF_ENUM` 
    - `SIM_BASE-NA-369_REF_ENUM` 
    - `SIM_BASE-NA-371_REF_ENUM` 
    - `SIM_BASE-NA-373_REF_ENUM` 
    - `SIM_BASE-NA-375_REF_ENUM` 
    - `SIM_BASE-NA-377_REF_ENUM` 
    - `SIM_BASE-NA-379_REF_ENUM` 
    - `SIM_BASE-NA-381_REF_ENUM` 
    - `SIM_BASE-NA-383_REF_ENUM` 
    - `SIM_BASE-NA-385_REF_ENUM` 
    - `SIM_BASE-NA-387_REF_ENUM` 
    - `SIM_BASE-NA-389_REF_ENUM` 
    - `SIM_BASE-NA-391_REF_ENUM` 
    - `SIM_BASE-NA-393_REF_ENUM` 
    - `SIM_BASE-NA-395_REF_ENUM` 
    - `SIM_BASE-NA-397_REF_ENUM` 
    - `SIM_BASE-NA-399_REF_ENUM` 
    - `SIM_BASE-NA-401_REF_ENUM` 
    - `SIM_BASE-NA-403_REF_ENUM` 
    - `SIM_BASE-NA-405_REF_ENUM` 
    - `SIM_BASE-NA-407_REF_ENUM` 
    - `SIM_BASE-NA-409_REF_ENUM` 
    - `SIM_BASE-NA-411_REF_ENUM` 
    - `SIM_BASE-NA-413_REF_ENUM` 
    - `SIM_BASE-NA-415_REF_ENUM` 
    - `SIM_BASE-NA-417_REF_ENUM` 
    - `SIM_BASE-NA-419_REF_ENUM` 
    - `SIM_BASE-NA-421_REF_ENUM` 
    - `SIM_BASE-NA-423_REF_ENUM` 
    - `SIM_BASE-NA-425_REF_ENUM` 
    - `SIM_BASE-NA-427_REF_ENUM` 
    - `SIM_BASE-NA-429_REF_ENUM` 
    - `SIM_BASE-NA-431_REF_ENUM` 
    - `SIM_BASE-NA-433_REF_ENUM` 
    - `SIM_BASE-NA-435_REF_ENUM` 
    - `SIM_BASE-NA-437_REF_ENUM` 
    - `SIM_BASE-NA-439_REF_ENUM` 
    - `SIM_BASE-NA-441_REF_ENUM` 
    - `SIM_BASE-NA-443_REF_ENUM` 
    - `SIM_BASE-NA-513_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-293_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-295_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-297_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-299_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-301_REF_ENUM` 
    - `SIM_CONCEPTION-1-463_REF_ENUM` 
    - `SIM_CONCEPTION-1-465_REF_ENUM` 
    - `SIM_CONCEPTION-1-467_REF_ENUM` 
    - `SIM_CONCEPTION-1-469_REF_ENUM` 
    - `SIM_CONCEPTION-1-471_REF_ENUM` 
    - `SIM_CONCEPTION-1-473_REF_ENUM` 
    - `SIM_CONCEPTION-1-475_REF_ENUM` 
    - `SIM_CONCEPTION-1-477_REF_ENUM` 
    - `SIM_CONCEPTION-1-479_REF_ENUM` 
    - `SIM_CONCEPTION-1-481_REF_ENUM` 
    - `SIM_CONCEPTION-1-483_REF_ENUM` 
    - `SIM_CONCEPTION-1-485_REF_ENUM` 
    - `SIM_CONCEPTION-1-487_REF_ENUM` 
    - `SIM_CONCEPTION-1-489_REF_ENUM` 
    - `SIM_CONCEPTION-1-491_REF_ENUM` 
    - `SIM_REX-1-493_REF_ENUM` 
    - `SIM_REX-1-499_REF_ENUM` 
    - `SIM_REX-1-501_REF_ENUM` 
    - `SIM_REX-1-503_REF_ENUM` 
    - `SIM_REX-1-507_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-43_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-45_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-47_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-53_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-55_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-57_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-59_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-63_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-65_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-75_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-77_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-83_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-85_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-93_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-95_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-11_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-125_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-131_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-15_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-19_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-21_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-23_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-27_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-331_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-333_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-3_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-7_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-9_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-105_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-111_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-113_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-115_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-119_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-337_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-339_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-341_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-343_REF_ENUM` 
* `QUALEXLANGUE`
    - `FRA` FRA
    - `ENU` ENU

`QualUser` business object definition
-------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `usr_login`                                                  | regexp(100)                              | yes*     | yes       | yes      | Login                                                                            |
| `usr_last_name`                                              | char(50)                                 |          | yes       | yes      | Last name                                                                        |
| `usr_active`                                                 | enum(1) using `USER_STATUS` list         |          | yes       |          | -                                                                                |
| `usr_first_name`                                             | char(50)                                 |          | yes       | yes      | First name                                                                       |
| `qualUsrTests`                                               | multi(256) using `QUALEXTYPE` list       |          | yes       |          | -                                                                                |
| `usr_lang`                                                   | enum(3) using `LANG` list                | yes      | yes       | yes      | Language                                                                         |
| `usr_email`                                                  | email(100)                               |          | yes       | yes      | Email address                                                                    |
| `qualUsrLevel`                                               | multi(100) using `QUALEXDIFFICULTY` list |          | yes       |          | -                                                                                |
| `qualUsrTypedutilisateur`                                    | enum(100) using `QUALUSRTYPEDUTILISATEUR` list | yes      | yes       |          | -                                                                                |
| `qualUsrToken`                                               | char(100)                                |          |           |          | -                                                                                |
| `qualUsrUrlQuest`                                            | url(400)                                 |          | yes       |          | -                                                                                |

### Lists

* `USER_STATUS`
    - `0` Disabled
    - `1` Enabled
    - `2` Pending
    - `3` Web services only
* `QUALEXTYPE`
    - `UML` UML
    - `JAVA` JAVA
    - `JS` JS
    - `SQL` SQL
    - `SIM_BASE` Simplicité - Notions de base
    - `SIM_CONCEPTION` Simplicité - Conception
    - `SIM_OPERATION` Simplicité - Opération
    - `SIM_INTEGRATION` Simplicité - Intégration
    - `SIM_REX` Simplicité - Retour Formation
    - `SIM_CERTIF_COMP` Certification - Concepts généraux
    - `SIM_CERTIF_PARAM` Certification - Paramétrage
    - `SIM_CERTIF_DEV` Certification - Developpement Java
    - `SIM_CERTIF_DOC` Certification - Documentation
    - `SIM_CERTIF_INSTAL_EXP` Certification - Installation - Exploitation
    - `SIM_CERTIF_BEHAVE` Certification - Comportement
    - `SIM_CERTIF_HOOKS` Certification - Hooks
    - `NA` Non applicable
* `LANG`
    - `ENU` English language
    - `FRA` French language
* `QUALEXDIFFICULTY`
    - `1` Niveau 1
    - `2` Niveau 2
    - `3` Niveau 3
    - `NA` Non applicable
* `QUALUSRTYPEDUTILISATEUR`
    - `ADMIN` Administrateur
    - `CAND` Candidat
    - `GEN` Générique

### Custom actions

* `QualCandidateNotify`: 07/04/2021 Replaced by 'send notif'

`QualExam` business object definition
-------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `qualExamName`                                               | char(100)                                | yes*     | yes       |          | -                                                                                |
| `qualExamPublic`                                             | boolean                                  |          | yes       |          | -                                                                                |
| `qualExType`                                                 | enum(40) using `QUALEXTYPE` list         | yes      | yes       |          | -                                                                                |
| `qualExamDescription`                                        | text(3000)                               |          | yes       |          | -                                                                                |
| `qualExamCertId` link to **`QualCertif`**                    | id                                       |          | yes       |          | -                                                                                |
| _Ref. `qualExamCertId.qualCertNom`_                          | _char(100)_                              |          |           |          | -                                                                                |

### Lists

* `QUALEXTYPE`
    - `UML` UML
    - `JAVA` JAVA
    - `JS` JS
    - `SQL` SQL
    - `SIM_BASE` Simplicité - Notions de base
    - `SIM_CONCEPTION` Simplicité - Conception
    - `SIM_OPERATION` Simplicité - Opération
    - `SIM_INTEGRATION` Simplicité - Intégration
    - `SIM_REX` Simplicité - Retour Formation
    - `SIM_CERTIF_COMP` Certification - Concepts généraux
    - `SIM_CERTIF_PARAM` Certification - Paramétrage
    - `SIM_CERTIF_DEV` Certification - Developpement Java
    - `SIM_CERTIF_DOC` Certification - Documentation
    - `SIM_CERTIF_INSTAL_EXP` Certification - Installation - Exploitation
    - `SIM_CERTIF_BEHAVE` Certification - Comportement
    - `SIM_CERTIF_HOOKS` Certification - Hooks
    - `NA` Non applicable

`QualExamEx` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `qualExamexExamId` link to **`QualExam`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualExamexExamId.qualExamName`_                       | _char(100)_                              |          |           |          | -                                                                                |
| `qualExamexExId` link to **`QualExercise`**                  | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualExamexExId.qualExId`_                             | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExQuestion`_                       | _text(10000)_                            |          |           |          | -                                                                                |
| `qualExamexScore`                                            | int(100)                                 | yes      | yes       |          | -                                                                                |
| _Ref. `qualExamexExId.qualExAnswerText`_                     | _text(10000)_                            |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExType`_                           | _enum(40) using `QUALEXTYPE` list_       |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExRefenum`_                        | _enum(100) using `QUAL_REF_ENUM_CHOICES` list_ |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExAnswerType`_                     | _enum(100) using `QUALEXANWSERTYPE` list_ |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExAnswerEnumeration`_              | _char(200)_                              |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExChoicesEnumeration`_             | _text(10000)_                            |          |           |          | -                                                                                |

### Lists

* `QUALEXTYPE`
    - `UML` UML
    - `JAVA` JAVA
    - `JS` JS
    - `SQL` SQL
    - `SIM_BASE` Simplicité - Notions de base
    - `SIM_CONCEPTION` Simplicité - Conception
    - `SIM_OPERATION` Simplicité - Opération
    - `SIM_INTEGRATION` Simplicité - Intégration
    - `SIM_REX` Simplicité - Retour Formation
    - `SIM_CERTIF_COMP` Certification - Concepts généraux
    - `SIM_CERTIF_PARAM` Certification - Paramétrage
    - `SIM_CERTIF_DEV` Certification - Developpement Java
    - `SIM_CERTIF_DOC` Certification - Documentation
    - `SIM_CERTIF_INSTAL_EXP` Certification - Installation - Exploitation
    - `SIM_CERTIF_BEHAVE` Certification - Comportement
    - `SIM_CERTIF_HOOKS` Certification - Hooks
    - `NA` Non applicable
* `QUAL_REF_ENUM_CHOICES`
    - `Certification - Comportement-Niveau 1-303_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-305_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-307_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-309_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-311_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-313_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-315_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-317_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-135_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-137_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-139_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-141_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-143_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-277_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-279_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-281_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-283_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-285_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-287_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-289_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-291_REF_ENUM` 
    - `Certification - Hooks-Niveau 1-275_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-319_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-321_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-323_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-325_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-327_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-329_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-145_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-147_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-149_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-151_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-153_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-155_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-157_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-159_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-161_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-163_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-165_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-167_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-169_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-171_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-173_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-175_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-177_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-179_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-181_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-183_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-185_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-187_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-189_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-191_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-193_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-195_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-197_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-199_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-201_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-203_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-205_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-207_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-209_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-211_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-213_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-215_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-217_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-219_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-221_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-223_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-225_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-227_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-229_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-231_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-233_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-235_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-237_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-239_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-241_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-243_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-245_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-247_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-249_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-251_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-253_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-255_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-257_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-259_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-261_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-263_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-265_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-267_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-269_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-271_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-273_REF_ENUM` 
    - `JAVA-1-351_REF_ENUM` 
    - `SIM_BASE-1-445_REF_ENUM` 
    - `SIM_BASE-1-447_REF_ENUM` 
    - `SIM_BASE-1-449_REF_ENUM` 
    - `SIM_BASE-1-451_REF_ENUM` 
    - `SIM_BASE-1-453_REF_ENUM` 
    - `SIM_BASE-1-455_REF_ENUM` 
    - `SIM_BASE-1-457_REF_ENUM` 
    - `SIM_BASE-1-459_REF_ENUM` 
    - `SIM_BASE-1-461_REF_ENUM` 
    - `SIM_BASE-NA-345_REF_ENUM` 
    - `SIM_BASE-NA-353_REF_ENUM` 
    - `SIM_BASE-NA-355_REF_ENUM` 
    - `SIM_BASE-NA-357_REF_ENUM` 
    - `SIM_BASE-NA-359_REF_ENUM` 
    - `SIM_BASE-NA-361_REF_ENUM` 
    - `SIM_BASE-NA-363_REF_ENUM` 
    - `SIM_BASE-NA-365_REF_ENUM` 
    - `SIM_BASE-NA-367_REF_ENUM` 
    - `SIM_BASE-NA-369_REF_ENUM` 
    - `SIM_BASE-NA-371_REF_ENUM` 
    - `SIM_BASE-NA-373_REF_ENUM` 
    - `SIM_BASE-NA-375_REF_ENUM` 
    - `SIM_BASE-NA-377_REF_ENUM` 
    - `SIM_BASE-NA-379_REF_ENUM` 
    - `SIM_BASE-NA-381_REF_ENUM` 
    - `SIM_BASE-NA-383_REF_ENUM` 
    - `SIM_BASE-NA-385_REF_ENUM` 
    - `SIM_BASE-NA-387_REF_ENUM` 
    - `SIM_BASE-NA-389_REF_ENUM` 
    - `SIM_BASE-NA-391_REF_ENUM` 
    - `SIM_BASE-NA-393_REF_ENUM` 
    - `SIM_BASE-NA-395_REF_ENUM` 
    - `SIM_BASE-NA-397_REF_ENUM` 
    - `SIM_BASE-NA-399_REF_ENUM` 
    - `SIM_BASE-NA-401_REF_ENUM` 
    - `SIM_BASE-NA-403_REF_ENUM` 
    - `SIM_BASE-NA-405_REF_ENUM` 
    - `SIM_BASE-NA-407_REF_ENUM` 
    - `SIM_BASE-NA-409_REF_ENUM` 
    - `SIM_BASE-NA-411_REF_ENUM` 
    - `SIM_BASE-NA-413_REF_ENUM` 
    - `SIM_BASE-NA-415_REF_ENUM` 
    - `SIM_BASE-NA-417_REF_ENUM` 
    - `SIM_BASE-NA-419_REF_ENUM` 
    - `SIM_BASE-NA-421_REF_ENUM` 
    - `SIM_BASE-NA-423_REF_ENUM` 
    - `SIM_BASE-NA-425_REF_ENUM` 
    - `SIM_BASE-NA-427_REF_ENUM` 
    - `SIM_BASE-NA-429_REF_ENUM` 
    - `SIM_BASE-NA-431_REF_ENUM` 
    - `SIM_BASE-NA-433_REF_ENUM` 
    - `SIM_BASE-NA-435_REF_ENUM` 
    - `SIM_BASE-NA-437_REF_ENUM` 
    - `SIM_BASE-NA-439_REF_ENUM` 
    - `SIM_BASE-NA-441_REF_ENUM` 
    - `SIM_BASE-NA-443_REF_ENUM` 
    - `SIM_BASE-NA-513_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-293_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-295_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-297_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-299_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-301_REF_ENUM` 
    - `SIM_CONCEPTION-1-463_REF_ENUM` 
    - `SIM_CONCEPTION-1-465_REF_ENUM` 
    - `SIM_CONCEPTION-1-467_REF_ENUM` 
    - `SIM_CONCEPTION-1-469_REF_ENUM` 
    - `SIM_CONCEPTION-1-471_REF_ENUM` 
    - `SIM_CONCEPTION-1-473_REF_ENUM` 
    - `SIM_CONCEPTION-1-475_REF_ENUM` 
    - `SIM_CONCEPTION-1-477_REF_ENUM` 
    - `SIM_CONCEPTION-1-479_REF_ENUM` 
    - `SIM_CONCEPTION-1-481_REF_ENUM` 
    - `SIM_CONCEPTION-1-483_REF_ENUM` 
    - `SIM_CONCEPTION-1-485_REF_ENUM` 
    - `SIM_CONCEPTION-1-487_REF_ENUM` 
    - `SIM_CONCEPTION-1-489_REF_ENUM` 
    - `SIM_CONCEPTION-1-491_REF_ENUM` 
    - `SIM_REX-1-493_REF_ENUM` 
    - `SIM_REX-1-499_REF_ENUM` 
    - `SIM_REX-1-501_REF_ENUM` 
    - `SIM_REX-1-503_REF_ENUM` 
    - `SIM_REX-1-507_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-43_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-45_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-47_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-53_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-55_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-57_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-59_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-63_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-65_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-75_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-77_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-83_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-85_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-93_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-95_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-11_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-125_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-131_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-15_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-19_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-21_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-23_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-27_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-331_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-333_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-3_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-7_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-9_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-105_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-111_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-113_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-115_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-119_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-337_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-339_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-341_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-343_REF_ENUM` 
* `QUALEXANWSERTYPE`
    - `TXT` Texte
    - `ENUM` Énuméré
    - `MULTI_ENUM` Énuméré multiple
    - `DIAG` Diagramme

`QualExUsr` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `qualExusrUsrexamId` link to **`QualUserExam`**              | id                                       | *        | yes       |          | -                                                                                |
| _Ref. `qualExusrUsrexamId.qualUsrexamId`_                    | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `qualExusrUsrexamId.qualUsrexamUsrId`_                 | _id_                                     |          |           |          | -                                                                                |
| _Ref. `qualUsrexamUsrId.usr_login`_                          | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| _Ref. `qualExusrUsrexamId.qualUsrexamDateLimite`_            | _date_                                   |          |           |          | -                                                                                |
| _Ref. `qualExusrUsrexamId.qualUsrexamExamId`_                | _id_                                     |          |           |          | -                                                                                |
| _Ref. `qualUsrexamExamId.qualExamName`_                      | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `qualExusrExamexId.qualExamexExId`_                    | _id_                                     |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExId`_                             | _char(100)_                              |          |           |          | -                                                                                |
| `qualExusrUsrVirtualId` link to **`QualUser`**               | id                                       |          | yes       |          | -                                                                                |
| `qualExusrExamexId` link to **`QualExamEx`**                 | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualExusrExamexId.qualExamexExamId`_                  | _id_                                     |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExAnswerType`_                     | _enum(100) using `QUALEXANWSERTYPE` list_ |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExQuestion`_                       | _text(10000)_                            |          |           |          | -                                                                                |
| `qualExusrCheck`                                             | enum(100) using `QUALEXUSRCHECK` list    |          |           |          | -                                                                                |
| `qualExusrSubmitted`                                         | boolean                                  |          | yes       |          | -                                                                                |
| _Ref. `qualExamexExId.qualExRefenum`_                        | _enum(100) using `QUAL_REF_ENUM_CHOICES` list_ |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExAnswerEnumeration`_              | _char(200)_                              |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExAnswerText`_                     | _text(10000)_                            |          |           |          | -                                                                                |
| _Ref. `qualExusrExamexId.qualExamexScore`_                   | _int(100)_                               |          |           |          | -                                                                                |
| _Ref. `qualExamexExId.qualExType`_                           | _enum(40) using `QUALEXTYPE` list_       |          |           |          | -                                                                                |
| `qualExusrAnswer`                                            | text(10000)                              |          | yes       |          | -                                                                                |
| `qualExusrImage`                                             | document                                 |          | yes       |          | -                                                                                |
| `qualExusrAnswerEnumeration`                                 | enum(100) using `QUALEXUSRANSWERENUMERATION` list |          | yes       |          | -                                                                                |
| `qualExusrProgress`                                          | int(100)                                 |          |           |          | -                                                                                |

### Lists

* `QUALEXANWSERTYPE`
    - `TXT` Texte
    - `ENUM` Énuméré
    - `MULTI_ENUM` Énuméré multiple
    - `DIAG` Diagramme
* `QUALEXUSRCHECK`
    - `OK` OK
    - `KO` KO
    - `NA` NA
* `QUAL_REF_ENUM_CHOICES`
    - `Certification - Comportement-Niveau 1-303_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-305_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-307_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-309_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-311_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-313_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-315_REF_ENUM` 
    - `Certification - Comportement-Niveau 1-317_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-135_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-137_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-139_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-141_REF_ENUM` 
    - `Certification - Concepts généraux-Niveau 1-143_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-277_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-279_REF_ENUM` 
    - `Certification - Developpement Java-Niveau 1-281_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-283_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-285_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-287_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-289_REF_ENUM` 
    - `Certification - Documentation-Niveau 1-291_REF_ENUM` 
    - `Certification - Hooks-Niveau 1-275_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-319_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-321_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-323_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-325_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-327_REF_ENUM` 
    - `Certification - Installation / Exploitation-Niveau 1-329_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-145_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-147_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-149_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-151_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-153_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-155_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-157_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-159_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-161_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-163_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-165_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-167_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-169_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-171_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-173_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-175_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-177_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-179_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-181_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-183_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-185_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-187_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-189_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-191_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-193_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-195_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-197_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-199_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-201_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-203_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-205_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-207_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-209_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-211_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-213_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-215_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-217_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-219_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-221_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-223_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-225_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-227_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-229_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-231_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-233_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-235_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-237_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-239_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-241_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-243_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-245_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-247_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-249_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-251_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-253_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-255_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-257_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-259_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-261_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-263_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-265_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-267_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-269_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-271_REF_ENUM` 
    - `Certification - Paramétrage-Niveau 1-273_REF_ENUM` 
    - `JAVA-1-351_REF_ENUM` 
    - `SIM_BASE-1-445_REF_ENUM` 
    - `SIM_BASE-1-447_REF_ENUM` 
    - `SIM_BASE-1-449_REF_ENUM` 
    - `SIM_BASE-1-451_REF_ENUM` 
    - `SIM_BASE-1-453_REF_ENUM` 
    - `SIM_BASE-1-455_REF_ENUM` 
    - `SIM_BASE-1-457_REF_ENUM` 
    - `SIM_BASE-1-459_REF_ENUM` 
    - `SIM_BASE-1-461_REF_ENUM` 
    - `SIM_BASE-NA-345_REF_ENUM` 
    - `SIM_BASE-NA-353_REF_ENUM` 
    - `SIM_BASE-NA-355_REF_ENUM` 
    - `SIM_BASE-NA-357_REF_ENUM` 
    - `SIM_BASE-NA-359_REF_ENUM` 
    - `SIM_BASE-NA-361_REF_ENUM` 
    - `SIM_BASE-NA-363_REF_ENUM` 
    - `SIM_BASE-NA-365_REF_ENUM` 
    - `SIM_BASE-NA-367_REF_ENUM` 
    - `SIM_BASE-NA-369_REF_ENUM` 
    - `SIM_BASE-NA-371_REF_ENUM` 
    - `SIM_BASE-NA-373_REF_ENUM` 
    - `SIM_BASE-NA-375_REF_ENUM` 
    - `SIM_BASE-NA-377_REF_ENUM` 
    - `SIM_BASE-NA-379_REF_ENUM` 
    - `SIM_BASE-NA-381_REF_ENUM` 
    - `SIM_BASE-NA-383_REF_ENUM` 
    - `SIM_BASE-NA-385_REF_ENUM` 
    - `SIM_BASE-NA-387_REF_ENUM` 
    - `SIM_BASE-NA-389_REF_ENUM` 
    - `SIM_BASE-NA-391_REF_ENUM` 
    - `SIM_BASE-NA-393_REF_ENUM` 
    - `SIM_BASE-NA-395_REF_ENUM` 
    - `SIM_BASE-NA-397_REF_ENUM` 
    - `SIM_BASE-NA-399_REF_ENUM` 
    - `SIM_BASE-NA-401_REF_ENUM` 
    - `SIM_BASE-NA-403_REF_ENUM` 
    - `SIM_BASE-NA-405_REF_ENUM` 
    - `SIM_BASE-NA-407_REF_ENUM` 
    - `SIM_BASE-NA-409_REF_ENUM` 
    - `SIM_BASE-NA-411_REF_ENUM` 
    - `SIM_BASE-NA-413_REF_ENUM` 
    - `SIM_BASE-NA-415_REF_ENUM` 
    - `SIM_BASE-NA-417_REF_ENUM` 
    - `SIM_BASE-NA-419_REF_ENUM` 
    - `SIM_BASE-NA-421_REF_ENUM` 
    - `SIM_BASE-NA-423_REF_ENUM` 
    - `SIM_BASE-NA-425_REF_ENUM` 
    - `SIM_BASE-NA-427_REF_ENUM` 
    - `SIM_BASE-NA-429_REF_ENUM` 
    - `SIM_BASE-NA-431_REF_ENUM` 
    - `SIM_BASE-NA-433_REF_ENUM` 
    - `SIM_BASE-NA-435_REF_ENUM` 
    - `SIM_BASE-NA-437_REF_ENUM` 
    - `SIM_BASE-NA-439_REF_ENUM` 
    - `SIM_BASE-NA-441_REF_ENUM` 
    - `SIM_BASE-NA-443_REF_ENUM` 
    - `SIM_BASE-NA-513_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-293_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-295_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-297_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-299_REF_ENUM` 
    - `SIM_CERTIF_INSTAL_EX-Niveau 1-301_REF_ENUM` 
    - `SIM_CONCEPTION-1-463_REF_ENUM` 
    - `SIM_CONCEPTION-1-465_REF_ENUM` 
    - `SIM_CONCEPTION-1-467_REF_ENUM` 
    - `SIM_CONCEPTION-1-469_REF_ENUM` 
    - `SIM_CONCEPTION-1-471_REF_ENUM` 
    - `SIM_CONCEPTION-1-473_REF_ENUM` 
    - `SIM_CONCEPTION-1-475_REF_ENUM` 
    - `SIM_CONCEPTION-1-477_REF_ENUM` 
    - `SIM_CONCEPTION-1-479_REF_ENUM` 
    - `SIM_CONCEPTION-1-481_REF_ENUM` 
    - `SIM_CONCEPTION-1-483_REF_ENUM` 
    - `SIM_CONCEPTION-1-485_REF_ENUM` 
    - `SIM_CONCEPTION-1-487_REF_ENUM` 
    - `SIM_CONCEPTION-1-489_REF_ENUM` 
    - `SIM_CONCEPTION-1-491_REF_ENUM` 
    - `SIM_REX-1-493_REF_ENUM` 
    - `SIM_REX-1-499_REF_ENUM` 
    - `SIM_REX-1-501_REF_ENUM` 
    - `SIM_REX-1-503_REF_ENUM` 
    - `SIM_REX-1-507_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-43_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-45_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-47_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-53_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-55_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-57_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-59_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-63_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-65_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-75_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-77_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-83_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-85_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-93_REF_ENUM` 
    - `Simplicité - Conception-Niveau 1-95_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-11_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-125_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-131_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-15_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-19_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-21_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-23_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-27_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-331_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-333_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-3_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-7_REF_ENUM` 
    - `Simplicité - Notions de base-Niveau 1-9_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-105_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-111_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-113_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-115_REF_ENUM` 
    - `Simplicité - Retour Formation-Niveau 1-119_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-337_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-339_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-341_REF_ENUM` 
    - `Simplicité-Notionsdebase-Nonapplicable-343_REF_ENUM` 
* `QUALEXTYPE`
    - `UML` UML
    - `JAVA` JAVA
    - `JS` JS
    - `SQL` SQL
    - `SIM_BASE` Simplicité - Notions de base
    - `SIM_CONCEPTION` Simplicité - Conception
    - `SIM_OPERATION` Simplicité - Opération
    - `SIM_INTEGRATION` Simplicité - Intégration
    - `SIM_REX` Simplicité - Retour Formation
    - `SIM_CERTIF_COMP` Certification - Concepts généraux
    - `SIM_CERTIF_PARAM` Certification - Paramétrage
    - `SIM_CERTIF_DEV` Certification - Developpement Java
    - `SIM_CERTIF_DOC` Certification - Documentation
    - `SIM_CERTIF_INSTAL_EXP` Certification - Installation - Exploitation
    - `SIM_CERTIF_BEHAVE` Certification - Comportement
    - `SIM_CERTIF_HOOKS` Certification - Hooks
    - `NA` Non applicable
* `QUALEXUSRANSWERENUMERATION`
    - `A` code A
    - `B` code B
    - `C` code C
    - `SQL_QST_1_CHOICES_ENUM_0` code C
    - `SQL_QST_1_CHOICES_ENUM_1` code C

### Custom actions

* `QUAL_SUBMITANSWER`: Submit action for candidate's answers

`QualCertif` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `qualCertNom`                                                | char(100)                                | yes*     | yes       |          | -                                                                                |

`QualCertUsr` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `qualCertusrCertId` link to **`QualCertif`**                 | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualCertusrCertId.qualCertNom`_                       | _char(100)_                              |          |           |          | -                                                                                |
| `qualCertusrUsrId` link to **`QualUser`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualCertusrUsrId.usr_login`_                          | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| _Ref. `qualCertusrUsrId.usr_first_name`_                     | _char(50)_                               |          |           | yes      | _First name_                                                                     |
| _Ref. `qualCertusrUsrId.usr_last_name`_                      | _char(50)_                               |          |           | yes      | _Last name_                                                                      |
| _Ref. `qualCertusrUsrId.qualUsrToken`_                       | _char(100)_                              |          |           |          | -                                                                                |
| `qualCertusrUrlEval`                                         | url(200)                                 |          | yes       |          | -                                                                                |
| `qualCertusrNote`                                            | char(100)                                |          |           |          | -                                                                                |

`QualUserExam` business object definition
-----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `qualUsrexamId`                                              | char(100)                                | yes*     | yes       |          | -                                                                                |
| `qualUsrexamUsrId` link to **`QualUser`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualUsrexamUsrId.usr_login`_                          | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| _Ref. `qualUsrexamUsrId.usr_last_name`_                      | _char(50)_                               |          |           | yes      | _Last name_                                                                      |
| _Ref. `qualUsrexamUsrId.usr_first_name`_                     | _char(50)_                               |          |           | yes      | _First name_                                                                     |
| `qualUsrexamExamId` link to **`QualExam`**                   | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualUsrexamExamId.qualExamName`_                      | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `qualUsrexamExamId.qualExamPublic`_                    | _boolean_                                |          |           |          | -                                                                                |
| `qualUsrexamDateLimite`                                      | date                                     |          | yes       |          | -                                                                                |
| `qualUsrexamEtat`                                            | enum(100) using `QUALUSREXAMETAT` list   |          | yes       |          | -                                                                                |
| `qualUsrexamScore`                                           | int(100)                                 |          | yes       |          | -                                                                                |
| `qualUsrexamTotalPoints`                                     | int(100)                                 |          | yes       |          | -                                                                                |
| `qualUsrexamCertusrId` link to **`QualCertUsr`**             | id                                       |          | yes       |          | -                                                                                |
| _Ref. `qualUsrexamCertusrId.qualCertusrCertId`_              | _id_                                     |          |           |          | -                                                                                |
| _Ref. `qualCertusrCertId.qualCertNom`_                       | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `qualUsrexamCertusrId.qualCertusrUsrId`_               | _id_                                     |          |           |          | -                                                                                |
| _Ref. `qualCertusrUsrId.usr_login`_                          | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |

### Lists

* `QUALUSREXAMETAT`
    - `TODO` À faire
    - `DONE` Terminé
    - `SCORED` Noté

`QualUsrExamSubjects` business object definition
------------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `qualUsrexamUsrId` link to **`QualUser`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualUsrexamUsrId.usr_login`_                          | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| `qualUsrexamExamId` link to **`QualExam`**                   | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `qualUsrexamExamId.qualExamName`_                      | _char(100)_                              |          |           |          | -                                                                                |

`QualCreateQuizz` business process definition
---------------------------------------------



### Activities

* `Begin`: 
* `CreateQuizz`: 
* `SelectQuestions`: 
* `SelectOtherQuestions`: 
* `LinkToUser`: 
* `SelectUsers`: 
* `SelectOtherUsers`: 
* `End`: 

`QualCreateCandidate` external object definition
------------------------------------------------




`QualEndTestExt` external object definition
-------------------------------------------




`QualPostCertif` external object definition
-------------------------------------------




`QualPostTraining` external object definition
---------------------------------------------




`QualStartTestExt` external object definition
---------------------------------------------




