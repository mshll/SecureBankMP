package com.coded.bankmp.service;

import com.coded.bankmp.bo.*;
import com.coded.bankmp.bo.UserResponse;
import com.coded.bankmp.entity.BankAccountEntity;
import com.coded.bankmp.entity.RoleEntity;
import com.coded.bankmp.entity.TransactionEntity;
import com.coded.bankmp.entity.UserEntity;
import com.coded.bankmp.exception.UserNotFoundException;
import com.coded.bankmp.repository.BankAccountRepository;
import com.coded.bankmp.repository.RoleRepository;
import com.coded.bankmp.repository.TransactionRepository;
import com.coded.bankmp.repository.UserRepository;
import com.coded.bankmp.util.Roles;
import com.coded.bankmp.util.TransactionType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BankAccountRepository bankAccountRepository;
    private final RoleRepository roleRepository;
    private final TransactionRepository transactionRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, BankAccountRepository bankAccountRepository, RoleRepository roleRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.bankAccountRepository = bankAccountRepository;
        this.roleRepository = roleRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        UserEntity userEntity = new UserEntity(request);
        userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        RoleEntity userRole = new RoleEntity(Roles.valueOf(request.getRole().toLowerCase()));
        userRole = roleRepository.save(userRole);

        userEntity.setRole(userRole);
        userEntity = userRepository.save(userEntity);

        BankAccountEntity bankAccountEntity = new BankAccountEntity(userEntity);
        bankAccountEntity = bankAccountRepository.save(bankAccountEntity);

        UserResponse response = new UserResponse(userEntity);
        return response;
    }

    @Override
    public UserResponse updateUser(UpdateUserProfileRequest request, UserEntity userEntity) {
        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            userEntity.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            userEntity.setEmail(request.getEmail());
        }

        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            userEntity.setPhone(request.getPhone());
        }

        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            userEntity.setAddress(request.getAddress());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        }

        userEntity = userRepository.save(userEntity);

        UserResponse response = new UserResponse(userEntity);
        return response;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        UserResponse response = new UserResponse(userEntity);
        return response;
    }

    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {


        List<BankAccountEntity> userBankAccounts = bankAccountRepository.findByUserId(id);

        if (userBankAccounts.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        bankAccountRepository.deleteAll(userBankAccounts);
//        userRepository.deleteById(id); // cascade handles this.
    }

    public BankAccountEntity getBankAccountByUserId(Long id, int index) {
        List<BankAccountEntity> userBankAccounts = bankAccountRepository.findByUserId(id);
        if (userBankAccounts.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        return userBankAccounts.get(index);
    }


    @Override
    public TransactionResponse makeTransaction(Double amount, TransactionType type, UserEntity userEntity) {
        BankAccountEntity bankAccountEntity = getBankAccountByUserId(userEntity.getId(), 0);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(LocalDateTime.now());
        transactionEntity.setTransactionType(type);
        transactionEntity.setBankAccount(bankAccountEntity);

        if (type == TransactionType.DEPOSIT) {
            bankAccountEntity.setBalance(bankAccountEntity.getBalance() + amount);

        } else if (type == TransactionType.WITHDRAW) {
            bankAccountEntity.setBalance(bankAccountEntity.getBalance() - amount);
        }

        transactionEntity = transactionRepository.save(transactionEntity);

        TransactionResponse response = new TransactionResponse(transactionEntity.getId().toString(), type.toString(), transactionEntity.getCreateTime().toString(), amount.toString(), userEntity.getId().toString());
        return response;
    }

}
