create table if not exists parameter_versions
(
    id varchar(36) not null comment '序號 primary key',
    active boolean not null comment '是否啟用',
    version varchar(255) not null comment '版本號',
    version_description	varchar(255) not null comment '係數資料庫版本描述 (英文)',
    parameter_source varchar(255) not null comment '資料來源',
    company_id varchar(36) comment 'companies.id',
    customize int not null comment '0.係數資料庫 1.自訂係數',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment 'Ecoinvent係數和環保署碳足跡係數版本';

-- Ecoinvent係數和環保署碳足跡係數係數值
create table if not exists parameter_values
(
    id varchar(36) not null comment '序號 primary key',
    version_id varchar(36) comment 'parameter_versions.id',
    paramter_category varchar(255) not null comment '係數類別',
    parameter_type varchar(255) not null comment '係數型別, 繁中',
    parameter_type_en varchar(255) not null comment '係數型別, 英文',
    description text not null comment '係數描述, 繁中',
    description_en text not null comment '係數描述, 英文',
    code varchar(255) not null comment '係數編號',
    unit varchar(255) not null comment '係數單位',
    parameter_year varchar(255) not null comment '係數年份',
    area varchar(255) not null comment '係數地區',
    greenhouse_gas varchar(255) not null comment '溫室氣體種類',
    parameter_remark text comment '備註',
    parameter_value decimal(24,12) not null comment '係數值',
    company_id varchar(36) comment 'companies.id',
    customize int not null comment '0.係數資料庫 1.自訂係數',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment 'Ecoinvent係數和環保署碳足跡係數值';

-- Arx係數值
create table if not exists ar_gwp_values
(
    id varchar(36) not null comment '序號 primary key',
    ingredient_name varchar(255) not null comment '排放源名稱 (英文)',
    chemical_formula varchar(255) not null comment '化學式',
    alias varchar(255) not null comment '別名',
    gwptype int comment '0: 其他關注類物質; 1: 冷媒; 2: 消防; 3: 冷媒+消防',
    ghgtype varchar(255) comment '歸屬之七大溫室氣體族群',
    ar4_gwp_25_value decimal(24,12) not null comment 'AR4 GWP-25值',
    ar4_gwp_100_value decimal(24,12) not null comment 'AR4 GWP-100值',
    ar4_gwp_500_value decimal(24,12) not null comment 'AR4 GWP-500值',
    ar5_gwp_25_value decimal(24,12) not null comment 'AR5 GWP-25值',
    ar5_gwp_100_value decimal(24,12) not null comment 'AR5 GWP-100值',
    ar5_gwp_500_value decimal(24,12) not null comment 'AR5 GWP-500值',
    ar6_gwp_25_value decimal(24,12) not null comment 'AR6 GWP-25值',
    ar6_gwp_100_value decimal(24,12) not null comment 'AR6 GWP-100值',
    ar6_gwp_500_value decimal(24,12) not null comment 'AR6 GWP-500值',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment 'Arx係數值';

-- 公司資訊
create table if not exists companies
(
    id varchar(36) not null comment '序號 primary key',
    vendor boolean not null default false comment '是否為外部廠商',
    vendor_code varchar(20) comment '外部廠商代碼',
    status int not null comment '啟用狀態, 0:審核中、1:啟用中、2:停用中',
    chinese_name varchar(30) not null comment '公司中文名稱',
    english_name varchar(30) comment '公司英文名稱',
    gui_number varchar(20) not null comment '統一編號, Government Uniform Invoice number',
    registration_address varchar(50) not null comment '註冊地址',
    business_address varchar(50) not null comment '經營地址',
    person_in_charge varchar(30) comment '負責人',
    telephone_number varchar(30) comment '公司電話',
    fax_number varchar(30) comment '公司傳真',
    website_url varchar(20) comment '公司網站',
    industry_classification int not null comment 'SASB行業別, １~19 對應 A~S大類',
    capital_amounts bigint comment '資本額',
    industry_description varchar(50) comment '產業描述',
    main_products varchar(50) comment '主要產品',
    contact_person_name varchar(30) comment '聯絡人中文姓名',
    contact_person_telephone_number varchar(30) comment '聯絡人電話號碼',
    contact_person_mobile_number varchar(30) comment '聯絡人行動電話號碼',
    contact_person_email varchar(50) comment '聯絡人電子郵件信箱',
    preferred_contact_method int comment '偏好聯繫方式',
    logo varchar(30) comment '公司 Logo',
    system_settings text not null comment '系統設定值, JSON, 包含逾時登出分鐘數、以噸為單位的小數點位數、...',
    notify_settings text not null comment '通知管理, JSON',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '公司資訊';

-- 金鑰
create table if not exists golden_keys
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment 'companies.id',
    gui_number varchar(20) not null comment '統編, Government Uniform Invoice number',
    company_name varchar(30) not null comment '公司名稱',
    encrypt_json text not null comment '功能模組, JSON, 加密後',
    rowhash text not null comment '行雜湊',
    function_scope int not null comment '函式範圍',
    active_start_date date not null comment '可用起日',
    active_end_date date not null comment '可用迄日',
    company_admin_limit int not null comment '總使用者上限',
    inventory_limit int not null comment '總盤查數上限',
    list_report_limit int not null comment '總盤查清冊上限',
    certify_company_limit int not null comment '總查證使用者上限',
    inventory_user_limit int not null comment '總盤查員使用者上限',
    supplier_limit int not null comment '總外部廠商使用者上限',
    company_admin_use int comment '總使用者數量',
    inventory_use int comment '總盤查數量',
    list_report_use int comment '總盤查清冊數量',
    certify_company_use int comment '總查證使用者數量',
    inventory_user_use int comment '總盤查員使用者數量',
    supplier_use int comment '總外部廠商使用者數量',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '金鑰';

-- 帳號資訊
create table if not exists accounts
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment 'companies.id',
    user_name varchar(50) not null comment '使用者帳號',
    password_hash varchar(255) not null comment '使用者密碼, 加密後',  
    name varchar(30) not null comment '姓名',
    telephone_number varchar(30) not null comment '電話號碼',
    email varchar(50) not null comment '電子郵件信箱',
    avatar varchar(30) comment '頭像',
    status int not null comment '是否啟用, 0:待定、1:已啟用、2:已停用',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '帳號資訊';

-- 角色權限
create table if not exists roles
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) comment 'companies.id',
    system_default boolean not null default true comment '是否為系統預設',
    role_name int not null comment '角色名稱, 0:管理員、1.盤查員、2.外部廠商、3.查證人員、4.審查員',
    permission text not null comment '權限描述, JSON',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '角色權限';

-- 帳號權限
create table if not exists user_roles
(
    id varchar(36) not null comment '序號 primary key',
    account_id varchar(36) not null comment 'accounts.id',
    role_id varchar(36) not null comment 'roles.id',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '帳號權限';

-- 資料變更紀錄
create table if not exists change_histories
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment '變更人 companies.id',
    user_id varchar(50) not null comment '變更人 accounts.id',
    table_name varchar(50) not null comment '資料表名稱',
    field_name varchar(50) not null comment '資料欄位名稱',
    source_id varchar(36) not null comment '變更資料的 primary key',
    old_value varchar(255) not null comment '原本的欄位值',
    new_value varchar(255) not null comment '新的欄位值',
    request_time timestamp not null comment '需求申請時間',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '資料變更紀錄';

-- 系統操作紀錄
create table if not exists request_records
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment 'companies.id',
    api_name varchar(255) comment '執行的 API 名稱',
    user_name varchar(50) not null comment '執行者名稱',
    gui_number varchar(20) not null comment '執行者公司統編, Government Uniform Invoice number',
    user_ip varchar(45) not null comment '執行者 IP address',
    request_method varchar(8) not null comment '執行的 HTTP request method',
    request_url varchar(255) not null comment '執行的 HTTP request UEL',
    request_param text comment '執行的 HTTP request parameter',
    response_body text comment '執行的 HTTP request Response',
    status int not null comment '執行回傳的 Status',
    error_msg varchar(255) comment '執行後回傳的錯誤訊息',
    request_time timestamp not null comment '執行的日期時間',
    cost_time int not null comment '執行花費時間',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '系統操作紀錄';

-- 檔名對照表
create table if not exists file_names
(
    id varchar(36) not null comment '序號 primary key',
    file_name varchar(100) not null comment '檔名',
    original_name varchar(100) not null comment '原始檔名',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '檔名對照表';

-- 盤查專案
create table if not exists inventories
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment 'companies.id',
    name varchar(30) not null comment '盤查專案名稱',
    status int not null comment '盤查狀態, 0:進行中、1:查證中、2:已完成',
    inventory_year int not null comment '盤查年度',
    initiator varchar(30) not null comment '建立者姓名', 
    ar_version int not null comment '使用Arx GWP 版本, 4:AR4、5:AR5、6:AR6',
    description varchar(255) comment '專案備註',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '盤查專案';

-- 盤查資訊
create table if not exists inventory_details
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventoris.id',
    gui_number varchar(20) comment '公司統編, Government Uniform Invoice number',
    protection_code varchar(20) not null comment '管制編號',
    factory_number varchar(20) comment '工廠登記證編號',
    business_address varchar(50) not null comment '地址',
    employee_number int not null comment '員工數, 0:10人以下、1:10~29人、2:30~49人、3:50~99人、4:100~249人、5:250~499人、6:500人以上',
    earnings bigint comment '年營收',
    person_in_charge varchar(30) not null comment '負責人姓名',
    contact_person_name varchar(30) not null comment '聯絡人姓名',
    contact_email varchar(50) not null comment '聯絡人電子郵件信箱',
    contact_telephone varchar(30) not null comment '聯絡人電話',
    contact_mobile varchar(30) comment '聯絡人行動電話號碼',
    setting_method int not null comment '設定方法, 1:營運控制法、2:股權比例法',
    equity_ratio decimal(5,2) comment '股權比例',
    factory_outside_included_area varchar(500) comment '廠址外涵蓋區域',
    factory_inside_excluded_area varchar(500) comment '廠址內扣除區域',
    registration_reason int not null comment '登錄原因, 1:自願性登錄、2:環境部申報要求、4:金管會永續發展路徑、8:其他, 可複選',
    reason_others varchar(255) comment '登錄原因其他原因',
    according_rule int not null comment '盤查依據規範, 1:ISO 14064-1:2006、2:ISO 14064-1:2018、4:ISO 14064-1/CNS、8:GHG Protocol、16:溫室氣體排放量盤查作業指引、32:溫室氣體排放量盤查登錄及查驗管理辦法、64:溫室氣體排放量申請作業指引、128:溫室氣體盤查議定書—企業會計與報告標準、256:其他, 可複選',
    role_others varchar(255) comment '盤查依據規範其他規範',
    third_party_verification boolean not null default false comment '是否有第三方查證',
    guarantee_level text comment '查證保證等級, JSON',
    verification_institution varchar(255) comment '查證機構名稱',
    review_dates text comment '查證日期, JSON',
    significant_threshold decimal(5,2) not null comment '顯著性門檻',
    substantive_threshold decimal(5,2) not null comment '實質性門檻',
    eliminate_threshold decimal(5,2) not null comment '排除門檻',
    inventory_benchmark boolean default false comment '是否為盤查基準年',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '盤查資訊';

-- 排放源
create table if not exists emission_sources
( 
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) comment 'companies.id',
    category int not null comment '類別, 1~6 對應 C1~C6',
    type varchar(255) not null comment '排放類型',
    name varchar(255) not null comment '排放源名稱',
    system_default boolean not null comment '是否系統預設',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '排放源';

-- 鑑別準則
create table if not exists identify_rules
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment 'companies.id',
    select_rule_item text not null comment '鑑別因子, JSON, 勾選之鑑別準則項目 List',
    scores int not null comment '顯著項目分數門檻',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '鑑別準則';

-- 鑑別準則項目
create table if not exists identify_rule_items
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment 'companies.id',
    system_default boolean not null comment '是否系統預設',
    item varchar(255) not null comment '準則項目名稱',
    description varchar(255) not null comment '準則項目說明',
    weight int not null comment '準則項目權重',
    levels text not null comment '準則項目等級, JSON 包含等級高中低,分數123,等級說明',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '鑑別準則項目';

-- 顯著性評估
create table if not exists significant_evaluations
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventories.id',
    category int not null comment '類別, 1~6 對應 C1~C6',
    type varchar(255) not null comment '排放類型',
    identify_factor text not null comment '鑑別因子分數, JSON, 勾選之鑑別準則項目分數 List',
    score int comment '鑑別總分',
    result varchar(255) comment '鑑別結果, 顯著、非顯著、...',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '顯著性評估';

-- 盤查排放源
create table if not exists evaluation_items
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventories.id',
    emission_source_id varchar(36) not null comment 'emission_sources.id',
    checked boolean not null comment '是否納入計算',
    reason varchar(30) comment '未納入計算原因',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '盤查排放源';

-- 原燃物料
create table if not exists fuel_materials
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) comment 'companies.id',
    emission_source_id varchar(36) comment 'emission_sources.id',    
    name varchar(255) not null comment '活動項目原始名稱',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '原燃物料';

-- 活動項目(基本資料)
create table if not exists activity_item_bases
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventories.id',
    name varchar(255) not null comment '活動項目名稱, 預設與 fuel_materials.name 相同',
    process varchar(255) not null comment '製程',
    equipment varchar(255) not null comment '設備',
    fuel_material_id varchar(36) not null comment 'fuel_materials.id',
    fuel_material_name varchar(255) comment '原燃物料名稱',
    checked boolean not null comment '是否勾選',
    greenhouse_gases text not null comment '可能產生的溫室氣體種類, JSON, CO2、CH4、N2O、HFCS、PFCS、SF6、NF3, 勾選1~3項',
    stream_electricity boolean not null default false comment '是否屬於汽電共生設備',
    transfer_unit varchar(255) comment '轉換後單位',
    collaborators varchar(255) comment '內部協作人員',
    outer_collaborators varchar(255) comment '外部廠商協作人員',
    verifier varchar(255) comment '查證人員',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '活動項目(基本資料)';

-- 佐證資料
create table if not exists reference_files
(
    id varchar(36) not null comment '序號 primary key',
    base_id varchar(36) not null comment 'activity_item_bases.id',
    file_id varchar(36) not null comment 'file_names.id',
    file_name varchar(255) not null comment '原始檔案名稱',
    file_date date not null comment '檔案日期',
    file_size int not null comment '檔案大小',
    reference_items text comment '關聯活動清單, JSON',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '佐證資料';

-- 活動清單
create table if not exists activity_items
(
    id varchar(36) not null comment '序號 primary key',
    base_id varchar(36) not null comment 'activity_item_bases.id',
    car_number varchar(30) comment '車號',
    name varchar(30) comment '活動名稱',
    usage_date date comment '日期',
    amount bigint comment '金額', 
    usage_amount decimal(14,4) comment '使用量',
    usage_unit varchar(255) comment '使用量單位',
    transfer_usage decimal(14,4) comment '轉換後使用量',
    transfer_unit varchar(255) comment '轉換後單位',
    bio_energy boolean comment '是否生質能源',
    equipment int comment '設備台數',
    intensity_amount decimal(14,4) comment '原始填充量',
    months int comment '使用月份',
    leakage_rate decimal(7,4) comment '設備逸散率',
    leakage_amount decimal(14,4) comment '逸散量',
    leakage_unit varchar(255) comment '逸散量單位',
    transfer_leakage decimal(14,4) comment '轉換後逸散量',
    source varchar(255) comment '來源, 國家、民間、其他',
    user_number varchar(30) comment '電號、蒸氣號、水號',
    bill_month varchar(7) comment '帳單月份, YYYY/MM',
    bill_days int comment '帳單使用日數', 
    usage_days int comment '實際使用日數',
    start_date date comment '單據統計起始日',
    end_date date comment '單據統計迄止日',
    usage_degrees decimal(14,4) comment '單據總使用度數',
    shipping_item varchar(30) comment '運送品項',
    vehicle_type varchar(30) comment '交通工具種類',
    start_location varchar(30) comment '運輸起點',
    end_location varchar(30) comment '運輸終點', 
    weight decimal(14,4) comment '貨物重量',
    weight_unit varchar(255) comment '重量單位',
    distance decimal(14,4) comment '行駛距離',
    distance_unit varchar(255) comment '距離單位',
    ton_km decimal(14,4) comment '延噸公里',
    person_number int comment '搭乘人數',
    man_km decimal(14,4) comment '延人公里',
    waste_disposal int comment '廢棄物處理分類, 0:回收、1:焚化、2:掩埋',
    item_remark varchar(255) comment '備注',
    reference_files text comment '佐證資料, JSON',
    status int not null default 0 comment '進度, 0: 待審核、1: 待修正、2: 通過',
    reason text comment '審查未通過原因, {"aaa", "bbb"}',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '活動清單';

-- 資料輸入指派
create table if not exists data_assignments
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment 'companies_id',
    base_id varchar(36) not null comment 'activity_item_bases.id',
    assignbment_id varchar(36) not null comment '指派人員 accounts.id',
    role int not null comment '角色, 0:盤查員、1:審查員、2:外部廠商用戶',
    deadline date not null comment '期限, 盤查員為盤查期限、審查員為審查期限',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '資料輸入指派';

-- 排放量計算
create table if not exists emissions
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventories.id',
    category int not null comment '類別, 1~6 對應 C1~C6',
    activity_item_base_id varchar(36) not null comment 'activity_item_bases.id',
    amount decimal(14,4) not null comment '活動數據加總',
    unit varchar(255) not null comment '活動數據單位',
    greenhouse_gas varchar(255) not null comment '溫室氣體種類, CO2、CH4、N2O、HFCS、PFCS、SF6、NF3',
    parameter_value decimal(24,12) not null comment '係數值',
    gwp_value decimal(24,12) not null default 1.0 comment 'GWP 值',
    emission decimal(24,12) not null comment '排放量值',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '排放量計算';

-- 數據品質
create table if not exists data_qualities
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventories.id',
    activity_item_base_id varchar(36) not null comment 'activity_item_bases.id',
    data_type varchar(255) not null comment '活動數據種類',
    data_type_level int not null comment '活動數據種類等級',
    credibility_type varchar(255) not null comment '活動數據可信種類',
    credibility_level int not null comment '活動數據可信等級',
    parameter_type varchar(255) not null comment '活動數據係數種類',
    parameter_level int not null comment '活動數據係數種類等級',
    score int not null comment '數據評分',
    level varchar(255) not null comment '數據等級',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id) 
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '數據品質';

-- 不確定性評估
create table if not exists uncertainties
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventories.id',
    emission_ratio decimal(5,2) not null comment '不確定性評估之排放佔比%',
    confidence_lower decimal(7,4) not null comment '不確定性95%信賴區間之下限',
    confidence_upper decimal(7,4) not null comment '不確定性95%信賴區間之上限',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '不確定性評估';
 
-- 不確定性評估清單
create table if not exists uncertainty_items
(
    id varchar(36) not null comment '序號 primary key',
    uncertainty_id varchar(36) not null comment 'uncertainties.id',
    activity_item_base_id varchar(36) not null comment 'activity_item_bases.id',
    activity_confidence_lower decimal(7,4) comment '活動數據之不確定性95%信賴區間之下限',
    activity_confidence_upper decimal(7,4) comment '活動數據之不確定性95%信賴區間之上限',
    activity_data_source varchar(255) comment '活動數據之不確定性數據來源',
    greenhouse_gas varchar(255) not null comment '溫室氣體種類, CO2、CH4、N2O、HFCS、PFCS、SF6、NF3',
    parameter_confidence_lower decimal(7,4) comment '排放係數之不確定性95%信賴區間之下限',
    parameter_confidence_upper decimal(7,4) comment '排放係數之不確定性95%信賴區間之上限',
    parameter_data_source varchar(255) comment '排放係數之不確定性資料來源',
    greenhouse_gas_confidence_lower decimal(7,4) comment '單一溫室氣體之不確定性95%信賴區間之下限',
    greenhouse_gas_confidence_upper decimal(7,4) comment '單一溫室氣體之不確定性9::5%信賴區間之上限',
    emission decimal(24,12) comment '碳排量',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '不確定性評估清單';

-- 不確定評估上下限列表
create table if not exists uncertainty_defaults
(
    id varchar(36) not null comment '序號 primary key',
    source varchar(255) not null comment '數據來源',
    category int not null comment '類別, 1~6 對應 C1~C6',
    type varchar(255) not null comment '排放類型',
    fuel_material varchar(255) not null comment '原燃物料',
    greenhouse_gas varchar(255) not null comment '溫室氣體種類, CO2、CH4、N2O、HFCS、PFCS、SF6、NF3',
    lower_bound decimal(7,4) not null comment '不確定性95%信賴區間之下限',
    upper_bound decimal(7,4) not null comment '不確定性95%信賴區間之上限',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '不確定評估上下限列表';

-- 歷史油價
create table if not exists oil_prices
(
    id varchar(36) not null comment '序號 primary key',
    oil_year int not null comment '年份',
    oil_month int not null comment '月份',
    oil_type varchar(255) not null comment '油品種類, ex. 92無鉛汽油、95無鉛汽油、...',
    oil_unit varchar(255) not null comment '單位, ex. 公升、加侖、...',
    oil_price decimal(6,2) not null comment '油價',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '歷史油價';

-- 內部查證計畫
create table if not exists internal_verifications
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventories.id',
    duration varchar(255) not null comment '計畫期間, YYYY-MM-dd~YYYY-MM-dd',
    place varchar(50) not null comment '地點',
    description varchar(500) comment '查證說明',
    status int not null comment '計畫狀態, 0.進行中、1.已完成',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '內部查證計畫';
 
-- 查檢表題庫
create table if not exists verification_article_pools
(
    id varchar(36) not null comment '序號 primary key',
    company_id varchar(36) not null comment 'companies.id',
    system_default boolean not null default false comment '是否為預設查檢表條文',
    article varchar(50) not null comment '條文',
    article_content varchar(50) not null comment '條文內容',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '查檢表題庫';
 
-- 內部查證查檢表
create table if not exists internal_verification_checklists
(
    id varchar(36) not null comment '序號 primary key',
    internal_verification_id varchar(36) not null comment 'internal_verifications.id',
    company_id varchar(36) comment 'companies.id',
--    system_default boolean not null default false comment '是否為預設查檢表',
    name varchar(50) not null comment '查檢表名稱',
    select_article text comment '勾選的條文, verification_article_pools.ids',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '內部查證查檢表';
 
-- 查檢編組
create table if not exists verification_group
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventory.id',
    checklist_id varchar(36) not null comment 'internal_verification_checklists.id',
    target varchar(255) comment '受稽對象',
    verifier varchar(255) comment '查證人員',
    status int not null default 0 comment '查檢表狀態, 0:未執行、1:執行中、2:已完成',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '查證編組';
 
-- 內部查證條文結果
create table if not exists internal_verification_article_results
(
    id varchar(36) not null comment '序號 primary key',
    checklist_id varchar(36) not null comment 'internal_verification_checklists.id',
 --   system_default boolean not null default false comment '是否為預設查檢表條文',
    article_pool_id varchar(36) not null comment 'verification_article_pools.id',
 --   article varchar(50) not null comment '條文',
 --   article_content varchar(50) not null comment '條文內容'
    detail text comment '查證與回覆狀況, JSON, ex, [ {result: 0, ,description: "x xx", correctionDate: "yyyy-mm-dd", replyMessage: "xxx", referenceFiles: ""}, ... ]',
    result int comment '查證結果, 0:觀察、1:符合、2:不符合、3:不適用',
 --   description varchar(100) comment '狀況說明',
 --   correction_date varchar(10) comment '矯正日期. yyyy-mm-dd',
 --   reply_message varchar(255) comment '被稽人員回覆',
 --   reference_files varchar(255) comment '佐證資料',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '內部查證查檢表查證結果';
 
-- 預設內部查證查檢表條文
-- create table if not exists verification_article_defaults
-- (
--    id varchar(36) not null comment '序號 primary key',
--    article varchar(50) not null comment '條文',
--    article_content varchar(50) not null comment '條文內容',
--    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
--    options_system text comment '系統端延伸資料, JSON',
--    options_user text comment '客戶端延伸資料, JSON',
--    created_at timestamp not null comment '建立日期/時間',
--    updated_at timestamp not null comment '編輯日期/時間',
--    created_by varchar(255) not null comment '建立者帳號',
--    updated_by varchar(255) not null comment '編輯者帳號',
--    primary key (id)
-- ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '預設內部查證查檢表條文';

-- 減量情境
create table if not exists reduction_scenarios
(
    id varchar(36) not null comment '序號 primary key',
    inventory_id varchar(36) not null comment 'inventories.id',
    base_year int not null comment '盤查基準年',
    target_year int not null comment '目標年',
    scenario2 int comment '減量情境2',
    scenario2_percent decimal(2, 0) comment '減量情境2 %',
    greenpower_cost decimal(7,2) comment '綠電預估費用(TWD/度)',
    power_decline_rate decimal(5,2) comment '預估電力係數每年較前一年下降比例 (%)',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '減量情境';

-- 系統預設資料
create table if not exists default_datas
(
    id varchar(36) not null comment '序號 primary key',
    category varchar(255) not null comment '預設資料歸屬群組, ex. 原燃物料, ...',
    system_default boolean not null default true comment '系統預設',
    companiy_id varchar(36) comment 'companies.id',
    name varchar(50) not null comment '預設資料名稱',
    default_value varchar(255) not null comment '預設資料值',
    delete_flag boolean not null default false comment '是否刪除, true: 刪除、false: 未刪除',
    options_system text comment '系統端延伸資料, JSON',
    options_user text comment '客戶端延伸資料, JSON',
    created_at timestamp not null comment '建立日期/時間',
    updated_at timestamp not null comment '編輯日期/時間',
    created_by varchar(255) not null comment '建立者帳號',
    updated_by varchar(255) not null comment '編輯者帳號',
    primary key (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci comment '系統預設資料';