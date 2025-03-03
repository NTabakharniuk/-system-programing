# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "generic/ubuntu2204"

  config.vm.provider "qemu" do |qemu|
    qemu.memory = "2048"                    # Обмеження до 3 ГБ для стабільної роботи
    qemu.cpus = 4                            # Кількість ядер
    qemu.machine = "virt"                    # ARM-сумісна машина
    qemu.arch = "aarch64"                    # Архітектура для Apple Silicon
    qemu.cpu = "cortex-a72"                  # ARM-сумісний процесор
    qemu.qemu_binary = "/opt/homebrew/bin/qemu-system-aarch64" # Шлях до QEMU
  end

  # Налаштування для зміни порту SSH
  config.vm.network "forwarded_port", guest: 22, host: 2222
end