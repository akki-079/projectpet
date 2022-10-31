package com.demo.spring.services;

import java.util.List;


import com.demo.spring.entity.Owner;
import com.demo.spring.util.Message;


public interface OwnerService {
	/**
	 * To find owner By owner id
	 * @param id
	 * @return owner
	 */
	public Owner findOwner(int id);
	/**
	 * To get list of all owners
	 * @return List of owner
	 */
	public List<Owner> listOwners();
	/**
	 * To add owner
	 * @param owner
	 * @return Message
	 */
	public Message addOwner(Owner owner);
	/**
	 * To delete an owner by owner id
	 * @param id
	 * @return Message
	 */
	public Message removeOwner(int id);
	/**
	 * To update owner details
	 * @param id
	 * @param phNumber
	 * @param owner
	 * @return Message
	 */
	public Message updateOwner(int id,String phNumber,Owner owner);
}
