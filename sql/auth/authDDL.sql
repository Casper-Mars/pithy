create schema if not exists template_pithy collate utf8_general_ci;

create table if not exists permission
(
	id bigint auto_increment comment '主键'
		primary key,
	create_time timestamp(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
	update_time timestamp(3) default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
	del tinyint(1) default 0 not null comment '逻辑删除标志位',
	permissionName varchar(50) not null comment '权限名称',
	url varchar(256) null comment '权限地址',
	identifier varchar(30) null comment '权限标识符',
	parent_id bigint default 0 not null comment '父权限id'
)
comment '权限表';

create table if not exists role
(
	id bigint auto_increment comment '主键'
		primary key,
	create_time timestamp(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
	update_time timestamp(3) default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
	role_name varchar(50) not null comment '角色名称',
	active tinyint(1) default 1 not null comment '是否启动标志位',
	del tinyint(1) default 0 not null comment '逻辑删除标志位',
	constraint role_role_name_uindex
		unique (role_name)
)
comment '角色';

create table if not exists role_permission
(
	id bigint auto_increment comment '主键'
		primary key,
	create_time timestamp(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
	update_time timestamp(3) default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
	del tinyint(1) default 0 not null comment '逻辑删除标记位',
	role_id bigint not null comment '角色id',
	permission bigint not null comment '权限id'
)
comment '角色权限关系表';

create table if not exists role_user
(
	id bigint auto_increment comment '主键'
		primary key,
	create_time timestamp(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
	update_time timestamp(3) default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
	role_id bigint not null comment '角色id',
	user_id bigint not null comment '用户id',
	del tinyint(1) default 0 not null comment '逻辑删除标记位'
)
comment '角色用户关系表';

