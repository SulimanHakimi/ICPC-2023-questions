# if you fight for it, you deserve it.
# if you deserve it, fight for it.
def solve(num_str):
    valid_chars = {'0', '1', '6', '8', '9'}
    for char in num_str:
        if char not in valid_chars:
            return False

    num2_list = []
    for i in range(-1, -len(num_str) - 1, -1):
        if num_str[i] == '6':
            num2_list.append('9')
        elif num_str[i] == '9':
            num2_list.append('6')
        else:
            num2_list.append(num_str[i])

    num2 = ''.join(num2_list)

    return num_str == num2


inp = input()
print(solve(inp))
