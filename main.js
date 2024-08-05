import{ world, system, Vector } from "@minecraft/server";
import "./config/loader";

var use_camera = {};
var use_command = {};
let config = {
	"dtp:dynamic_fpp": "true",
	"dtp:vertical_smooth": "true",
	"dtp:vertical_smooth_speed": "10",
	"dtp:ride_speed": "10",
	"dtp:ride_dist": "2",
	"dtp:crosshair_type": "0",
	"dtp:add_dist": "0",
	"dtp:advance_cam_rot": "true",
	"dtp:peek": "true",
	"dtp:peek_dist": "1",
	"dtp:scope": "1",
	"dtp:aim_dist": "1",
	"dtp:pbt": "true",
	"dtp:mainhand_fpp": "false",
	"dtp:mainhand_name": "",
	"dtp:free_look": "true",
	"dtp:add_x": "0",
	"dtp:add_y": "0",
	"dtp:cam_360": "false",
	"dtp:cam_max": "20",
	"dtp:cam_speed": "5",
	"dtp:cam_tooltip": "false",
	"dtp:follow": "false",
	"dtp:follow_strength": "15",
	"dtp:preset": "0"
};

const config_preset = [
	{
		"dtp:dynamic_fpp": "true",
		"dtp:ride_speed": "10",
		"dtp:free_look": "false",
		"dtp:cam_speed": "5",
		"dtp:vertical_smooth": "true",
		"dtp:vertical_smooth_speed": "10",
		"dtp:cam_360": "false",
		"dtp:mainhand_fpp": "false",
		"dtp:mainhand_name": "",
		"dtp:cam_tooltip": "true",
		"dtp:pbt": "true",
		"dtp:scope": "1",
		"dtp:crosshair_type": "0",
		"dtp:peek": "true",
		"dtp:cam_max": "21",
		"dtp:preset": "0",
		"dtp:follow_strength": "20",
		"dtp:advance_cam_rot": "false",
		"dtp:follow": "true",
		"dtp:aim_dist": "-1",
		"dtp:peek_dist": "0",
		"dtp:add_x": "-7",
		"dtp:add_dist": "-9",
		"dtp:add_y": "0",
		"dtp:ride_dist": "1"
	},
	{
		"dtp:dynamic_fpp": "true",
		"dtp:ride_speed": "10",
		"dtp:free_look": "true",
		"dtp:vertical_smooth": "true",
		"dtp:vertical_smooth_speed": "10",
		"dtp:cam_360": "false",
		"dtp:cam_tooltip": "true",
		"dtp:pbt": "true",
		"dtp:scope": "1",
		"dtp:crosshair_type": "0",
		"dtp:peek": "true",
		"dtp:preset": "0",
		"dtp:follow_strength": "15",
		"dtp:advance_cam_rot": "true",
		"dtp:follow": "false",
		"dtp:peek_dist": "3",
		"dtp:ride_dist": "2",
		"dtp:mainhand_fpp": "false",
		"dtp:mainhand_name": "",
		"dtp:add_dist": "0",
		"dtp:add_x": "0",
		"dtp:add_y": "0",
		"dtp:cam_speed": "4",
		"dtp:cam_max": "20",
		"dtp:aim_dist": "1"
	},
	{
		"dtp:dynamic_fpp": "true",
		"dtp:free_look": "true",
		"dtp:cam_speed": "5",
		"dtp:vertical_smooth": "true",
		"dtp:cam_360": "false",
		"dtp:mainhand_fpp": "false",
		"dtp:mainhand_name": "",
		"dtp:cam_tooltip": "true",
		"dtp:pbt": "true",
		"dtp:crosshair_type": "0",
		"dtp:cam_max": "21",
		"dtp:preset": "0",
		"dtp:advance_cam_rot": "false",
		"dtp:follow": "true",
		"dtp:peek_dist": "0",
		"dtp:add_dist": "40",
		"dtp:add_x": "0",
		"dtp:aim_dist": "5",
		"dtp:scope": "0",
		"dtp:follow_strength": "43",
		"dtp:add_y": "2",
		"dtp:ride_dist": "6",
		"dtp:ride_speed": "20",
		"dtp:vertical_smooth_speed": "60",
		"dtp:peek": "false"
	},
	{
		"dtp:dynamic_fpp": "true",
		"dtp:ride_speed": "10",
		"dtp:free_look": "false",
		"dtp:cam_speed": "5",
		"dtp:vertical_smooth": "true",
		"dtp:vertical_smooth_speed": "10",
		"dtp:cam_360": "false",
		"dtp:mainhand_fpp": "false",
		"dtp:mainhand_name": "",
		"dtp:cam_tooltip": "true",
		"dtp:pbt": "true",
		"dtp:crosshair_type": "0",
		"dtp:peek": "true",
		"dtp:cam_max": "21",
		"dtp:preset": "0",
		"dtp:advance_cam_rot": "false",
		"dtp:follow": "true",
		"dtp:peek_dist": "0",
		"dtp:ride_dist": "1",
		"dtp:follow_strength": "16",
		"dtp:aim_dist": "1",
		"dtp:add_dist": "0",
		"dtp:add_x": "0",
		"dtp:add_y": "1",
		"dtp:scope": "0"
	}
]

function getActor(){
	let actor;
	for(let actor_type of world.getDimension("minecraft:overworld").getEntitiesAtBlockLocation({x:0, y:320, z:0})){
		if(actor_type.typeId == "kc:config_loader"){
			actor = actor_type;
			return actor;
		}
	}
	return actor;
}

function getConfigFromActor(){
	let l = {};
	let actor = getActor();
	if(actor == undefined) return {};
	for(let a of actor.getTags()){
		let b = a.split("=");
		l[b[0]] = b[1];
	}
	return l;
}

let ui_temp = {};

function setUI(playerData, path, id){
	
	if(!ui_temp[playerData.id]){
		ui_temp[playerData.id] = { [id]: path };
	}else{
		if(ui_temp[playerData.id][id] == path) return;
	}
//	console.warn(path)
	playerData.runCommand("scriptevent ui_load:" + id + " " + path + id);
	ui_temp[playerData.id][id] = path;
}

system.afterEvents.scriptEventReceive.subscribe( s => {
	config = getConfigFromActor();
	// console.warn(JSON.stringify(config))
	let preset = Number(config["dtp:preset"]) - 1;
	if(preset != -1){
		let actor = getActor();
		if(actor == undefined) return;
		for(let a of actor.getTags()){
			if(a.includes("dtp:")){
				actor.removeTag(a);
			}
		}

		config = config_preset[preset];
		for(let data of Object.keys(config_preset[preset])){
			actor.addTag(data + "=" + config_preset[preset][data]);
		}
	}
}, { namespaces: [ "config_loaded" ]});

let player_aim = {};

let player_rot_view = {};
let player_look = {};


let player_in_action = {};
let player_fpp = {};
let player_rot = {};
let player_rot_temp = {};
let transision_rot = {};
let transision_lean = {};
let mount_speed = {};
let aim_dist = {};
let lean = {};

system.runInterval(() => {
	let peek_dist = Number(config["dtp:peek_dist"]);
	let aim_dist_option = Number(config["dtp:aim_dist"]);
	let max_dist = Number(config["dtp:cam_max"]);
	let camera_speed = Number(config["dtp:cam_speed"]);
	
	let additional_x = Number(config["dtp:add_x"]);
	let additional_y = Number(config["dtp:add_y"]);
	let set = (Number(config["dtp:ride_speed"])/10-1)*0.09;
	let follow_strength = Number(config["dtp:follow_strength"]);
	for(let playerData of world.getPlayers()){
		if(playerData.hasTag("disable_dtp")) continue;

		if(use_command[playerData.id] != undefined){
			let is_disabled = playerData.hasTag("dtp:togle_cam");
			if(is_disabled){
				playerData.removeTag("dtp:togle_cam");
			}else{
				playerData.addTag("dtp:togle_cam");
				playerData.runCommand("camera @s clear");
				setUI(playerData, "textures/gui/crosshair_3", "crosshair_update");
			}
			delete use_command[playerData.id];
		}
		
		if(use_camera[playerData.id] != undefined){
			if(playerData.isSneaking){
				playerData.isSneaking = false;
				delete use_camera[playerData.id];
				setUI(playerData, "", "camera_tooltip_update");
				if(playerData.hasTag("dtp:togle_cam")){
					playerData.runCommand("camera @s clear");
					setUI(playerData, "textures/gui/crosshair_3", "crosshair_update");
				}
				continue;
			}
			if(Math.abs(playerData.getVelocity().y) > 0.01) continue;
			let camera_data = use_camera[playerData.id];
			let view = playerData.getViewDirection();
			let replace_y = Math.atan2(view.x, view.z);
			let rot = playerData.getRotation();
			rot.z = 0;
			let buffer_rot = camera_data.rotation;
			buffer_rot.z = 0;
			let delta_camera = Vector.subtract(playerData.location, camera_data.location);
			let speed = delta_camera.length();
			let speed_rot = Vector.distance(rot, buffer_rot);
			
			if(speed > 0 || speed_rot > 0.01){
				let delta_camera_rotation = {
					x: rot.x - buffer_rot.x,
					y: rot.y - buffer_rot.y
				};
				delta_camera_rotation.x = (delta_camera_rotation.x + 360) % 360;
				delta_camera_rotation.y = (delta_camera_rotation.y + 360) % 360;
				if(delta_camera_rotation.y > 180){
					delta_camera_rotation.y = -(360 - delta_camera_rotation.y);
				}
				
				if(delta_camera_rotation.x > 180){
					delta_camera_rotation.x = -(360 - delta_camera_rotation.x);
				}
				
				let offset = camera_data.offset;

				if(speed > 0){
					let motion = -(Math.atan2(delta_camera.x, delta_camera.z) - replace_y);
					let motion_x = Math.cos(motion);
					let motion_y = Math.sin(motion);
					
					let rotX = (offset.rotation.x + 45) * Math.PI/180;
					let rotY = (offset.rotation.y) * Math.PI/180;
					speed *= motion_x;
					let aa = {
						x: 0,
						y: (Math.cos(rotX)*speed-Math.sin(rotX)*speed),
						z: (Math.sin(rotX)*speed+Math.cos(rotX)*speed),
					};
					let calc_loc = {
						x: (Math.cos(rotY)*aa.x-Math.sin(rotY)*aa.z),
						y: aa.y,
						z: (Math.sin(rotY)*aa.x+Math.cos(rotY)*aa.z),
					};
					calc_loc.x = calc_loc.x*camera_speed;
					calc_loc.y = (calc_loc.y + motion_y*0.1)*camera_speed;
					calc_loc.z = calc_loc.z*camera_speed;
					let estimate_loc = {
						x: calc_loc.x + offset.location.x,
						y: calc_loc.y + offset.location.y,
						z: calc_loc.z + offset.location.z
					}
					let block = playerData.dimension.getBlock(estimate_loc);
					if(!block.isSolid & Vector.distance(estimate_loc, camera_data.location) < max_dist){
						use_camera[playerData.id].offset.location.x += calc_loc.x;
						use_camera[playerData.id].offset.location.y += calc_loc.y;
						use_camera[playerData.id].offset.location.z += calc_loc.z;
					}
					
				}

				if(speed_rot > 0.01){
					let estimate_rot = (offset.rotation.x + delta_camera_rotation.x*camera_speed*0.3);
					if(config["dtp:cam_360"] == "false"){
						if(Math.sin((estimate_rot - 90) / 180 * Math.PI) < 0) use_camera[playerData.id].offset.rotation.x = estimate_rot;
					}else{
						use_camera[playerData.id].offset.rotation.x = estimate_rot;
					}
					
					use_camera[playerData.id].offset.rotation.y = (offset.rotation.y + delta_camera_rotation.y*camera_speed*0.3);
				}
				

				playerData.teleport(camera_data.location, {rotation: camera_data.rotation});
				playerData.runCommand("camera @s set dtp:dynamic_third_person ease 0.1 linear pos " + offset.location.x + " " + offset.location.y + " " + offset.location.z + " rot " + offset.rotation.x + " " + offset.rotation.y);
			}

			
			continue;
		}

		if(playerData.hasTag("dtp:togle_cam")) continue;
		let set_crosshair  = "textures/gui/crosshair_0";
		let item = playerData.getComponent("minecraft:equippable").getEquipment("Mainhand");
		if(config["dtp:mainhand_fpp"] == "true"){
			if(item && config["dtp:mainhand_name"].includes(item.typeId.split(":")[1])){
				playerData.runCommand("camera @s set minecraft:first_person");
				if(config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_3";
				player_fpp[playerData.id] = 2;
			}
		}
		if(item && item.typeId == "minecraft:crossbow"){
			player_aim[playerData.id] = 2;
		}
		if(item == undefined || (player_aim[playerData.id] == 2 && item.typeId != "minecraft:crossbow")){
			player_aim[playerData.id] = false;
		}
		
		player_in_action[playerData.id] = Math.max(player_in_action[playerData.id] - 0.025, 0);
		
		let loc = playerData.getHeadLocation();
		let view = playerData.getViewDirection();
		let rot = playerData.getRotation();
		let vel = playerData.getVelocity();
		let speed = Math.sqrt(Math.pow(vel.x, 2) + Math.pow(vel.y, 2) + Math.pow(vel.z, 2));
		let speed2d = Math.sqrt(Math.pow(vel.x, 2) + Math.pow(vel.z, 2));
		let pos = playerData.location;

		if(!player_rot[playerData.id]){
			player_rot[playerData.id] = rot;
			transision_rot[playerData.id] = 0;
			player_in_action[playerData.id] = 0;
			player_look[playerData.id] = 32;
			player_rot_temp[playerData.id] = 0;
			transision_lean[playerData.id] = 0;
			mount_speed[playerData.id] = 0;
			aim_dist[playerData.id] = 0;
			player_fpp[playerData.id] = 0;
			lean[playerData.id] = 1;
		}
		player_fpp[playerData.id] = Math.max(player_fpp[playerData.id] - 1, 0);
		
		if(speed2d > 0.025 && config["dtp:free_look"] == "true"){
			player_rot_temp[playerData.id] = (Math.atan2(vel.x, -vel.z)*180/3.14-180);
		}
		
		let is_on_water = playerData.isInWater;
		let is_riding = playerData.hasComponent("minecraft:riding");
		if(is_riding){
			let getRide = playerData.getComponent("minecraft:riding").entityRidingOn;
			let getRidingData = getRide.getComponent("minecraft:rideable");
			let seat = 0;
			for(let i of getRidingData.getRiders()){
				if(i.id == playerData.id){
					break;
				}
				seat++;
			}

			rot.y = getRide.getRotation().y+getRidingData.getSeats()[seat].lockRiderRotation + 180;
			let rotX = (rot.x+45) * Math.PI/180;
			let rotY = (rot.y) * Math.PI/180;
			
			view = {
				"x": 0,
				"y": (Math.cos(rotX)-Math.sin(rotX)),
				"z": (Math.sin(rotX)+Math.cos(rotX)),
			};
			view = {
				"x": (Math.cos(rotY)*view.x-Math.sin(rotY)*view.z),
				"y": view.y,
				"z": (Math.sin(rotY)*view.x+Math.cos(rotY)*view.z),
			};
			vel = getRide.getVelocity();
		}
		
		if(config["dtp:dynamic_fpp"] == "true"){
			let air_size = 0;
			let perspective_scan = [
				{x : 1, y: 1, z: 0},
				{x : -1, y: 1, z: 0},
				{x : 0, y: 1, z: 1},
				{x : 0, y: 1, z: -1},
				{x : 1, y: 1, z: 1},
				{x : 1, y: 1, z: -1},
				{x : -1, y: 1, z: -1},
				{x : -1, y: 1, z: 1}
			]
			for(let pov of perspective_scan){
				if(playerData.dimension.getBlockFromRay({ x: pos.x, y: pos.y+2,  z: pos.z}, pov, {maxDistance : 4, includePassableBlocks: false})) air_size++;
				if(air_size == 4) break;
			}
			
			if(air_size > 3){
				playerData.runCommand("camera @s set minecraft:first_person");
				if(config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_3";
				
				let runId = system.runInterval(() => {
					playerData.addTag("cancel_third_person");
				});
				
				let runClear = system.runInterval(() => {
					playerData.removeTag("cancel_third_person");
					system.clearRun(runClear);
					system.clearRun(runId);
				}, 10);
				player_fpp[playerData.id] = 10;
			}
			
			if(playerData.hasTag("cancel_third_person")){
				playerData.runCommand("camera @s set minecraft:first_person");
				if(config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_3";
				player_fpp[playerData.id] = 10;
			}
		}
		
		let rot_temp = Math.floor((Math.atan2(vel.z, vel.x) * 180 / Math.PI - 90));
		if(rot_temp < -180){
			rot_temp = rot_temp+360;
		}
		
		let delta_rot = Math.abs(rot_temp - Math.floor((rot.y)));
		delta_rot = Math.min(delta_rot, (360-delta_rot));
		
		let dir_transision = 1;
		
		let perspective = Math.min(Math.max((player_rot[playerData.id].y - rot.y), -10), 10);
		if(transision_rot[playerData.id] > 0){
			if(perspective < transision_rot[playerData.id]){
				dir_transision = 0.3;
			}
		}else if(transision_rot[playerData.id] < 0){
			if(perspective > transision_rot[playerData.id]){
				dir_transision = 0.3;
			}
		}
			
		if(config["dtp:advance_cam_rot"] == "true"){
			dir_transision = dir_transision*(Math.min(speed*3, 1)*0.9+0.1);
			
			transision_rot[playerData.id] = Math.min(Math.max((transision_rot[playerData.id] + perspective * dir_transision * 0.5)*(0.9975 + Math.min(speed*3, 1)*0.0025), -15), 15);
		}
		player_rot[playerData.id] = rot;
		
		let camera_option = {
			x: 0,
			y: 2,
			dist: 1,
			y_min: 0.5,
			rot_x: 0,
			rot_y: 0,
			ease: 0.1
		}
		
		let smooth_y = 0.5;
		
		if(is_riding && config["dtp:ride_speed"] != "0"){
			camera_option.ease = 0.2;
			mount_speed[playerData.id] = Math.min((mount_speed[playerData.id] + speed*(0.46+set*4))*(0.9+set), Number(config["dtp:ride_dist"]));
			camera_option.dist = 1+mount_speed[playerData.id];
		}
		
		if(config["dtp:advance_cam_rot"] == "true"){
			camera_option.x += Math.max(Math.min(transision_rot[playerData.id], 10), -10)*0.75;
			camera_option.rot_y += Math.max(Math.min(transision_rot[playerData.id], 10), -10)*0.75;
		}
		
		camera_option.x += additional_x;
		camera_option.y += additional_y;
		
		camera_option.dist += Math.sqrt(Math.pow(additional_x+0.01, 2) + Math.pow(additional_y+0.01, 2))*0.1;
	
		if(playerData.isSneaking && config["dtp:peek"] == "true"){
			if(config["dtp:advance_cam_rot"] == "true" && lean[playerData.id] == 0){
				if(transision_rot[playerData.id] > 1){
					lean[playerData.id] = -1;
				}else if(transision_rot[playerData.id] < -1){
					lean[playerData.id] = 1;
				}
			}
			transision_lean[playerData.id] = (transision_lean[playerData.id] + perspective)*0.9;
			if(config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_2";
			
			if(transision_lean[playerData.id] > 10){
				lean[playerData.id] = 1;
			}else if(transision_lean[playerData.id] < -10){
				lean[playerData.id] = -1;
			}
			camera_option.x = -7*lean[playerData.id];
			camera_option.y = 3;
			camera_option.rot_y = -5*lean[playerData.id];
			camera_option.dist += peek_dist;
			camera_option.ease = 0.3;
		}else{
			lean[playerData.id] = 0;
		}

		if(player_aim[playerData.id] && config["dtp:scope"] == "1"){
			if(config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_1";
			aim_dist[playerData.id] = aim_dist[playerData.id] + 0.02;
			
			let item = playerData.getComponent("minecraft:equippable").getEquipment("Mainhand");
			
			if(item.typeId == "minecraft:spyglass"){
				playerData.runCommand("camera @s set minecraft:first_person");
				if(config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_3";
				player_fpp[playerData.id] = 1;
			}
			camera_option.x = (-6-Math.min(aim_dist[playerData.id], 1)*2)*((transision_rot[playerData.id] > 0 && config["dtp:advance_cam_rot"] == "true") ? -1 : 1);
			camera_option.y = 1;
			camera_option.rot_y = (transision_rot[playerData.id] < 0 && config["dtp:advance_cam_rot"] == "true") ? 5 : -5;
			camera_option.dist -= Math.min(aim_dist[playerData.id], 1)*(1 + aim_dist_option*0.1);
			camera_option.dist += (1 + aim_dist_option);
			camera_option.dist = Math.max(camera_option.dist, 1.3);
			camera_option.ease = 0.2;
		}else{
			if(aim_dist[playerData.id] != 0) aim_dist[playerData.id] = 0;
		}
		
		if(player_aim[playerData.id] && config["dtp:scope"] == "2"){
			playerData.runCommand("camera @s set minecraft:first_person");
			if(config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_3";
			player_fpp[playerData.id] = 2;
		}
		
		if(player_in_action[playerData.id] > 0 && config["dtp:free_look"] == "true" ){
			player_rot_temp[playerData.id] = rot.y;
		}
		
		if(player_aim[playerData.id] || playerData.isSneaking || is_riding || is_on_water || config["dtp:free_look"] == "false" || player_fpp[playerData.id] > 0){
			player_rot_temp[playerData.id] = rot.y;
			playerData.playAnimation("animation.third_person.default", {
				controller: "third_person_rot",
				blendOutTime: 1
			})
		}else{
			let animation;
			let blend = 20;
			
			animation = "animation.third_person.body_angle_" + Math.floor(-player_rot_temp[playerData.id]);
			if(delta_rot < 10 && speed > 0){
				animation = "animation.third_person.body2";
			}
			
			
			playerData.playAnimation(animation, {
				controller: "third_person_rot",
				blendOutTime: blend
			})
		}

		let rotX = (rot.x+45 + camera_option.y*10) * Math.PI/180;
		let rotY = (rot.y + camera_option.x*10) * Math.PI/180;
		
		let rot_add = {
			"x": 0,
			"y": (Math.cos(rotX)-Math.sin(rotX)),
			"z": (Math.sin(rotX)+Math.cos(rotX)),
		};
		rot_add = {
			"x": (Math.cos(rotY)*rot_add.x-Math.sin(rotY)*rot_add.z),
			"y": rot_add.y,
			"z": (Math.sin(rotY)*rot_add.x+Math.cos(rotY)*rot_add.z),
		};
		camera_option.dist += Number(config["dtp:add_dist"])/10;
		if(config["dtp:follow"] == "true") camera_option.dist += (Math.min(delta_rot, 180)/180)*speed;
		
		let loc_res = {
			x: loc.x-(view.x+rot_add.x)*camera_option.dist,
			y: loc.y-(view.y+rot_add.y)*camera_option.dist,
			z: loc.z-(view.z+rot_add.z)*camera_option.dist
		}

		//stabilize dist
		let view_res = {
			x: -(view.x+rot_add.x),
			y: -(view.y+rot_add.y),
			z: -(view.z+rot_add.z)
		};
		 let compiled_dist = Math.sqrt(Math.pow(view_res.x, 2) + Math.pow(view_res.y, 2) + Math.pow(view_res.z, 2));
		// let player_head = playerData.getHeadLocation();
		// let dimension = playerData.dimension;
		// let loc_to_cam_dist;
		
		// let dist_block = compiled_dist*camera_option.dist;
		// let collision_buffer = {
		// 	x: player_head.x + view_res.x/compiled_dist*2,
		// 	y: player_head.y + view_res.y/compiled_dist*2,
		// 	z: player_head.z + view_res.z/compiled_dist*2
		// };
		// for(let i = 2; i < dist_block; i += 0.1){
		// 	let loc_collision = {
		// 		x: player_head.x + view_res.x/compiled_dist*i,
		// 		y: player_head.y + view_res.y/compiled_dist*i,
		// 		z: player_head.z + view_res.z/compiled_dist*i
		// 	}
		// 	let collision = dimension.getBlockFromRay(loc_collision, { x: -view_res.x, y: -view_res.y, z: -view_res.z}, {maxDistance: 1, includePassableBlocks: false});
		// 	if(collision != undefined){
		// 		loc_res = {
		// 			x: collision_buffer.x - view_res.x/compiled_dist,
		// 			y: collision_buffer.y - view_res.y/compiled_dist,
		// 			z: collision_buffer.z - view_res.z/compiled_dist
		// 		};
		// 		loc_to_cam_dist = i;
		// 		break;
		// 	}

		// 	collision_buffer = loc_collision;
		// }

		view_res.x /= compiled_dist;
		view_res.y /= compiled_dist;
		view_res.z /= compiled_dist;
		let dist_block = compiled_dist*camera_option.dist * 2;
		let collision = playerData.dimension.getBlockFromRay(loc, view_res, {maxDistance: dist_block, includePassableBlocks: false});
		let loc_to_cam_dist = dist_block;
		let loc_to_pos_dist = dist_block;
		if(collision != undefined){
			let loc_collision = Vector.add(collision.block.location, collision.faceLocation);
			loc_to_cam_dist = Vector.distance(loc_collision, loc);
			loc_to_pos_dist = Vector.distance(loc_collision, pos);

			loc_res = {
				x: loc_collision.x - view_res.x,
				y: loc_collision.y - view_res.y,
				z: loc_collision.z - view_res.z
			};
		}
		
		if(config["dtp:dynamic_fpp"] == "true" && (loc_to_cam_dist < 1.5 || loc_to_pos_dist < 1)){
			playerData.runCommand("camera @s set minecraft:first_person");
			if(config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_3";
			player_fpp[playerData.id] = 2;
		}

		let rot_res = {
			x: rot.x+camera_option.rot_x,
			y: rot.y+camera_option.rot_y
		}
		camera_option.ease += (1-Math.min(delta_rot, 180)/180)*speed*0.5;
		
		if(config["dtp:vertical_smooth"] == "true"){
			if(!player_rot_view[playerData.id]) player_rot_view[playerData.id] = loc_res.y;
			smooth_y *= Number(config["dtp:vertical_smooth_speed"])/10;
			if(vel.y == 0) smooth_y = 10;
			player_rot_view[playerData.id] = (player_rot_view[playerData.id]*(1-Math.min((Math.abs(vel.y)*0.9+0.1)*smooth_y, 1)) + loc_res.y*(Math.min((Math.abs(vel.y)*0.9+0.1)*smooth_y, 1)));
			loc_res.y = player_rot_view[playerData.id];
		}
		
		if(config["dtp:crosshair_type"] != "0"){
			set_crosshair = "textures/gui/crosshair_" + (Number(config["dtp:crosshair_type"])-1);
		}
		
		if(player_fpp[playerData.id] > 0){
			set_crosshair = "textures/gui/crosshair_3";
		}

		setUI(playerData, set_crosshair, "crosshair_update");
		if(player_fpp[playerData.id] > 0) continue;
		
		if(rot_res.x > 0){
			rot_res.x = Math.min(rot_res.x, 90);
			let temp_rot = rot_res.x/90;
			temp_rot = Math.pow(Math.sin(temp_rot*3.14/2), 2);
			loc_res.x = loc_res.x * (1 - temp_rot) + pos.x * temp_rot;
			loc_res.z = loc_res.z * (1 - temp_rot) + pos.z * temp_rot;
		}
		
		if(config["dtp:pbt"] == "true"){
			let target_entity = playerData.getEntitiesFromViewDirection({maxDistance: 64});
			let block_select_dist = player_look[playerData.id];
			
			let select_speed = 0.01;
			
			if(target_entity.length > 0){
				block_select_dist = target_entity[0].distance;
				if(block_select_dist < 7 && config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_2";
				select_speed = 0.01 + (1 - Math.abs(block_select_dist-player_look[playerData.id])/128)*0.04;
			}else{
				
				let target_block = playerData.getBlockFromViewDirection({maxDistance: 128});
				if(target_block != undefined){
					let block_pos = Vector.add(target_block.block.location, target_block.faceLocation);
					
					block_select_dist = Math.sqrt(Math.pow(block_pos.x - loc.x , 2) + Math.pow(block_pos.y - loc.y , 2) + Math.pow(block_pos.z - loc.z , 2));
					if(block_select_dist < 7 && config["dtp:crosshair_type"] == "0") set_crosshair = "textures/gui/crosshair_2";
					select_speed = 0.01 + (1 - Math.abs(block_select_dist-player_look[playerData.id])/128)*0.04;
				}
			}
			
			if(block_select_dist < 7) setUI(playerData, set_crosshair, "crosshair_update");
			
			player_look[playerData.id] = player_look[playerData.id]*(1-select_speed) + block_select_dist*select_speed;
			
			let target_pos = { x: loc.x + view.x * player_look[playerData.id], y: loc.y + view.y * player_look[playerData.id],  z: loc.z + view.z * player_look[playerData.id] };
			let delta_target_pos = { x: loc_res.x - target_pos.x, y: loc_res.y - target_pos.y,  z: loc_res.z - target_pos.z};
			let delta_target_pos_xz = Math.sqrt(Math.pow(delta_target_pos.x, 2) + Math.pow(delta_target_pos.z, 2));
			let replace_rot_y =  Math.atan2(delta_target_pos.x, -delta_target_pos.z) * 180 / Math.PI;
			let replace_rot_x =  Math.atan2(delta_target_pos_xz, -delta_target_pos.y) * 180 / Math.PI;
			if(replace_rot_y < -180){
				replace_rot_y = replace_rot_y+360;
			}
			rot_res.y = replace_rot_y;
			rot_res.x = replace_rot_x-90;
		}
		
		if(config["dtp:follow"] == "true"){
			camera_option.ease = follow_strength / 100;
		}else{
			if(camera_option.ease > 0.5) camera_option.ease = 0.5;
			camera_option.ease *= 0.85;
		}

		playerData.runCommand("camera @s set dtp:dynamic_third_person ease " + camera_option.ease + " linear pos " + loc_res.x + " " + loc_res.y + " " + loc_res.z + " rot " + rot_res.x + " " + rot_res.y);
	}
});

var aim_duration = {};
system.runInterval(()=>{
	for(let id of Object.keys(aim_duration)){
		if(aim_duration[id] == 0){
			player_aim[id] = true;
			delete aim_duration[id];
		}
		aim_duration[id] -= 1;
	}
});

world.afterEvents.itemStartUse.subscribe( s => {
	aim_duration[s.source.id] = 2;
});

world.afterEvents.itemStopUse.subscribe( s => {
	delete player_aim[s.source.id];
	delete aim_duration[s.source.id];
});

world.afterEvents.itemUseOn.subscribe( s => {
	player_in_action[s.source.id] = 1;
});

world.afterEvents.itemUse.subscribe( s => {
	player_in_action[s.source.id] = 1;
});

world.afterEvents.playerBreakBlock.subscribe( s => {
	player_in_action[s.player.id] = 1;
});

world.afterEvents.playerPlaceBlock.subscribe( s => {
	player_in_action[s.player.id] = 1;
});

world.afterEvents.itemDefinitionEvent.subscribe( s => {
	player_in_action[s.source.id] = 1;
	if(s.eventName == "dtp:use_camera"){
		if(use_camera[s.source.id] != undefined){
			delete use_camera[s.source.id];
			setUI(s.source, "", "camera_tooltip_update");
			if(s.source.hasTag("dtp:togle_cam")){
				s.source.runCommand("camera @s clear");
				setUI(s.source, "textures/gui/crosshair_3", "crosshair_update");
			}
		}else{
			if(s.source.getVelocity().y == 0){
				if(config["dtp:cam_tooltip"] == "true") setUI(s.source, "show_tooltip", "camera_tooltip_update");
				use_camera[s.source.id] = {
					location: s.source.location,
					rotation: s.source.getRotation(),
					offset: {
						location: s.source.getHeadLocation(),
						rotation: s.source.getRotation()
					}
				};
			}
		}
	}
});

world.afterEvents.entityHitEntity.subscribe( s => {
	if(s.damagingEntity.typeId == "minecraft:player" && s.hitEntity){
		player_in_action[s.damagingEntity.id] = 1;
	}
	if(s.hitEntity && s.hitEntity.typeId == "minecraft:player"){
		if(use_camera[s.hitEntity.id] != undefined){
			delete use_camera[s.hitEntity.id];
			setUI(s.hitEntity, "", "camera_tooltip_update");
			if(s.hitEntity.hasTag("dtp:togle_cam")){
				s.hitEntity.runCommand("camera @s clear");
				setUI(s.hitEntity, "textures/gui/crosshair_3", "crosshair_update");
			}
		}
	}
});

world.afterEvents.entityHitBlock.subscribe( s => {
	player_in_action[s.damagingEntity.id] = 1;
}, {entityTypes: [ "minecraft:player" ]});

world.beforeEvents.chatSend.subscribe( s => {
	if(s.message != ".toggle_cam") return;
	s.cancel = true;
	use_command[s.sender.id] = {}
});