package com.pollapp.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pollapp.exception.IncorrectPasswordException;
import com.pollapp.dao.UserDao;
import com.pollapp.model.User;
import com.pollapp.service.UserService;

/**
 * Implementation of UserService
 * that provides transaction support
 * password encoding support
 * and delegates CRUD options to a dao.
 * @see com.pollapp.dao.UserDao
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private UserDao dao;
	private MutableAclService aclService;
	private PasswordEncoder encoder;
	
	
	@Autowired
	public UserServiceImpl(UserDao dao, MutableAclService service,PasswordEncoder encoder) {
		super();
		this.dao = dao;
		this.aclService = service;
		this.encoder = encoder;
	}

	@Override
	@Transactional
	public long createUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		long userId = dao.createUser(user);
		user.setId(userId);
		createAcl(user);
		return userId;
	}

	@Override
	@Transactional
	public void editUser(User user) {
		dao.updateUser(user);
	}

	@Override
	@Transactional
	public List<User> findUsers(String username) {
		return dao.findUsers(username);
	}

	@Override
	@Transactional
	public User getUserByUsername(String username) {
		return dao.retrieveUserByUsername(username);
	}

	@Override
	@Transactional
	public User getUserById(long userId) {
		return dao.retrieveUserById(userId);
	}

	@Override
	@Transactional
	public void deleteUser(long userId) {
		dao.deleteUser(userId);
		removeAcl(userId);
	}
	
	@Override
	@Transactional
    public void changePassword(String newPassword, String oldPassword,Principal principal) {
        if(!(checkPassword(principal.getName(), oldPassword))){
            throw new IncorrectPasswordException();
        }
        newPassword = encoder.encode(newPassword); 
        dao.changePassword(principal.getName(),newPassword);
    }
	
	/**
	 * Adds an access control list 
	 * granting the newly created user write and read permissions
	 * @param poll
	 */
	private void createAcl(User user){
		MutableAcl acl = aclService.createAcl(new ObjectIdentityImpl(user.getClass(), user.getId()));
		Sid sid = new PrincipalSid(user.getUsername());
		acl.setOwner(sid);
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, true);
		aclService.updateAcl(acl);
	}
	
	private void removeAcl(long userId){
		aclService.deleteAcl(new ObjectIdentityImpl(User.class, userId), true);
	}

	private boolean checkPassword(String principal, String password){
		return encoder.matches(password, dao.getPassword(principal));
	}

}
