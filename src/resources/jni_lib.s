	.file	"jni_lib.c"
	.text
	.comm	map_lock,40,32
	.local	map
	.comm	map,8192,32
	.comm	id_lock,40,32
	.local	id_counter
	.comm	id_counter,4,4
	.section	.rodata
.LC0:
	.string	"%s\n"
	.text
	.globl	loadSO
	.type	loadSO, @function
loadSO:
.LFB6:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$32, %rsp
	movq	%rdi, -24(%rbp)
	movq	-24(%rbp), %rax
	movl	$1, %esi
	movq	%rax, %rdi
	call	dlopen@PLT
	movq	%rax, -8(%rbp)
	cmpq	$0, -8(%rbp)
	jne	.L2
	call	dlerror@PLT
	movq	%rax, %rdx
	movq	stderr(%rip), %rax
	leaq	.LC0(%rip), %rsi
	movq	%rax, %rdi
	movl	$0, %eax
	call	fprintf@PLT
	movl	$1, %edi
	call	exit@PLT
.L2:
	leaq	id_lock(%rip), %rdi
	call	pthread_mutex_lock@PLT
	movl	id_counter(%rip), %eax
	movl	%eax, -12(%rbp)
	movl	id_counter(%rip), %eax
	addl	$1, %eax
	movl	%eax, id_counter(%rip)
	leaq	id_lock(%rip), %rdi
	call	pthread_mutex_unlock@PLT
	leaq	map_lock(%rip), %rdi
	call	pthread_mutex_lock@PLT
	movl	-12(%rbp), %eax
	cltq
	leaq	0(,%rax,8), %rcx
	leaq	map(%rip), %rdx
	movq	-8(%rbp), %rax
	movq	%rax, (%rcx,%rdx)
	leaq	map_lock(%rip), %rdi
	call	pthread_mutex_unlock@PLT
	movl	-12(%rbp), %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE6:
	.size	loadSO, .-loadSO
	.section	.rodata
	.align 8
.LC1:
	.string	"Java_dummyapijni_DummyAPI_00024_add_1one"
	.text
	.globl	call_add_one
	.type	call_add_one, @function
call_add_one:
.LFB7:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	pushq	%rbx
	subq	$56, %rsp
	.cfi_offset 3, -24
	movl	%edi, -52(%rbp)
	movl	%esi, -56(%rbp)
	movq	%fs:40, %rax
	movq	%rax, -24(%rbp)
	xorl	%eax, %eax
	movl	-52(%rbp), %eax
	cltq
	leaq	0(,%rax,8), %rdx
	leaq	map(%rip), %rax
	movq	(%rdx,%rax), %rax
	movq	%rax, -40(%rbp)
	leaq	-48(%rbp), %rbx
	movq	-40(%rbp), %rax
	leaq	.LC1(%rip), %rsi
	movq	%rax, %rdi
	call	dlsym@PLT
	movq	%rax, (%rbx)
	call	dlerror@PLT
	movq	%rax, -32(%rbp)
	cmpq	$0, -32(%rbp)
	je	.L5
	movq	stderr(%rip), %rax
	movq	-32(%rbp), %rdx
	leaq	.LC0(%rip), %rsi
	movq	%rax, %rdi
	movl	$0, %eax
	call	fprintf@PLT
	movl	$1, %edi
	call	exit@PLT
.L5:
	movq	-48(%rbp), %rdx
	movl	-56(%rbp), %eax
	movl	%eax, %edi
	call	*%rdx
	movq	-24(%rbp), %rcx
	xorq	%fs:40, %rcx
	je	.L7
	call	__stack_chk_fail@PLT
.L7:
	addq	$56, %rsp
	popq	%rbx
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE7:
	.size	call_add_one, .-call_add_one
	.globl	init_jni_lib
	.type	init_jni_lib, @function
init_jni_lib:
.LFB8:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	$0, %esi
	leaq	map_lock(%rip), %rdi
	call	pthread_mutex_init@PLT
	movl	$0, %esi
	leaq	id_lock(%rip), %rdi
	call	pthread_mutex_init@PLT
	nop
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE8:
	.size	init_jni_lib, .-init_jni_lib
	.section	.rodata
	.align 8
.LC2:
	.string	"/home/oliveryu/ffi_benchmarks/target/native/include/libdummyapi.so"
.LC3:
	.string	"%d"
	.text
	.globl	main
	.type	main, @function
main:
.LFB9:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$32, %rsp
	movl	%edi, -20(%rbp)
	movq	%rsi, -32(%rbp)
	movl	$0, %eax
	call	init_jni_lib
	leaq	.LC2(%rip), %rdi
	call	loadSO
	movl	%eax, -4(%rbp)
	movl	-4(%rbp), %eax
	movl	$10, %esi
	movl	%eax, %edi
	call	call_add_one
	movl	%eax, %esi
	leaq	.LC3(%rip), %rdi
	movl	$0, %eax
	call	printf@PLT
	movl	$0, %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE9:
	.size	main, .-main
	.ident	"GCC: (Ubuntu 9.4.0-1ubuntu1~20.04.1) 9.4.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 8
	.long	 1f - 0f
	.long	 4f - 1f
	.long	 5
0:
	.string	 "GNU"
1:
	.align 8
	.long	 0xc0000002
	.long	 3f - 2f
2:
	.long	 0x3
3:
	.align 8
4:
